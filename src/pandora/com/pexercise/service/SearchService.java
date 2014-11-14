package com.pexercise.service;

import com.pexercise.domain.Group;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * This service balances the use of a cache and a system of shards balancing the way the data could be split to allow
 * for speed with in memory cache and scaling through the use of smaller data subsets.
 */
public class SearchService {
    /**
     * This local memory cache is just to simulate what would likely evolve to an external cache system
     */
    private GroupKeywordCache cache;

    /**
     * This implementation is to simulate the use of a datasource
     */
    private Map<String, Shard> shardMap;

    private int maxShardCount;
    private LinkedList<Shard> shards;
    private String sourceFilePath;

    private int queryCount;
    private int cacheHit;

    public SearchService(String sourceFilePath, int maxSearchStringsToBeCached, int maxShardCount) {
        this.sourceFilePath = sourceFilePath;
        shardMap = new HashMap<String, Shard>();
        cache = new GroupKeywordCache(maxSearchStringsToBeCached);
        this.maxShardCount = maxShardCount > 0 ? maxShardCount : 100;
        shards = new LinkedList<Shard>();
        processSourceFile();
    }

    public String query(String query) {
        String result = "";
        if (query == null || query.isEmpty()) {
            return result;
        }
        queryCount++;
        String nfc = normalizeString(query);
        //attempt to read from cache
        Set<Group> groups = cache.get(nfc);
        if (groups == null) {
            Shard shard = shardMap.get(nfc);
            groups = shard == null ? null : shard.getGroups(nfc);

            //add shard to cache if not null
            if (groups != null) {
                cache.put(nfc, groups);
            }
        } else {
            cacheHit++;
        }
        return groups == null ? "" : getUrlString(groups);
    }

    private String getUrlString(Set<Group> groups) {
        StringBuilder buffer = new StringBuilder();
        for (Group group : groups) {
            for (String url : group.getUrls()) {
                if (buffer.length() > 0) {
                    buffer.append(",");
                }
                buffer.append(url);
            }
        }
        return buffer.toString();
    }

    protected int getShardCount() {
        return shards.size();
    }

    protected String getShardNameForSearchString(String searchString) {
        return shardMap.get(searchString).getName();
    }

    private void processSourceFile() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(new File(this.sourceFilePath)));
            String line = br.readLine(); //trim the header
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void processLine(String line) throws IOException {
        String[] values = line.split("\t");
        if (values[0].isEmpty()) {
            return;
        }
        String nfc = normalizeString(values[0]);

        //currently only creating shards for complete group names
        Shard shard = createShard(nfc);

        //update the shard
        shard.addGroup(nfc, new Group(values[0], values[1]));

        //update the cache
        cache.put(nfc, shard.getGroups(nfc));
    }

    private String normalizeString(String value) {
        return Normalizer.normalize(value, Normalizer.Form.NFC);
    }

    /*
        Captured the logic to start splitting the groups by word and allow for individual word matching
        private void processIndividualSearchWords(String groupName, String url) throws IOException {
            for (String searchKey : new TreeSet<String>(Arrays.asList(groupName.split("\\s+")))) {
                Shard indexFile = createShard(searchKey);
                //Update the shard
            }
        }
    */

    private Shard createShard(String searchKey) throws IOException {
        Shard shard = shardMap.get(searchKey);
        if (shard == null) {
            //create new shard or add to an existing one
            int shardCount = shards.size();
            if (shardCount < maxShardCount) {
                shard = new Shard("shard_" + shardCount);
            } else {
                //rotate the shard for storage
                shard = shards.remove();
            }
            shards.add(shard);
            shardMap.put(searchKey, shard);
        }
        return shard;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public int getCacheHit() {
        return cacheHit;
    }

    /**
     * This class is to simulate usage of a storage shard for maintaining smaller data sets.  The purpose
     * is to enable returning multiple groups tied to a given search string.  This could be done with a
     * database or a flat file etc.
     */
    private class Shard {
        private String name;
        private Map<String, SortedSet<Group>> keyGroupsMap;

        private Shard(String name) {
            this.name = name;
            keyGroupsMap = new TreeMap<String, SortedSet<Group>>();
        }

        protected String getName() {
            return name;
        }

        protected void addGroup(String searchKey, Group group) {
            if (searchKey == null || searchKey.isEmpty() || group == null) {
                return;
            }
            SortedSet<Group> groups = keyGroupsMap.get(searchKey);
            if (groups == null) {
                //create sorted set that defines equality by group name
                groups = new TreeSet<Group>(new Comparator<Group>() {
                    @Override
                    public int compare(Group o1, Group o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                });
                groups.add(group);
                keyGroupsMap.put(searchKey, groups);
            } else {
                //update groups
                Object[] groupsArray = groups.toArray();
                int existingGroupIndex = Arrays.binarySearch(groupsArray, group, (Comparator<Object>) groups.comparator());
                if (existingGroupIndex >= 0) {
                    ((Group) groupsArray[existingGroupIndex]).addUrls(group.getUrls());
                } else {
                    groups.add(group);
                }
            }
        }

        protected Set<Group> getGroups(String searchString) {
            if (searchString == null || searchString.isEmpty()) {
                return new HashSet<Group>();
            }
            Set<Group> groups = keyGroupsMap.get(searchString);
            return groups == null ? new HashSet<Group>() : new HashSet<Group>(groups);
        }
    }
}
