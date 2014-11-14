package com.pexercise.service;

import com.pexercise.domain.Group;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The purpose of this class is to enable a key word based cache for sets of groups
 * associated with the key.  It also is meant to be size limited to scale for memory
 * considerations.  When space is needed the list that has the oldest retrieval time
 * stamp will be ejected from the cache.
 */
public class GroupKeywordCache {
    private int maxItems;
    private Map<String, CacheNode> cache;

    /**
     * Constructor to enable specifying a max size.
     *
     * @param maxItems - Limits the number of key mapped groupings that can be held in cache
     */
    public GroupKeywordCache(int maxItems) {
        this.maxItems = maxItems > 0 ? maxItems : 1000;
        //Synchronize the backing map and set to ensure thread safety for the cache
        cache = Collections.synchronizedMap(new TreeMap<String, CacheNode>());
    }

    /**
     * Returns the number of search keys with groups associated with them
     *
     * @return int value of the number of search key groups in the cache
     */
    public int size() {
        return cache.size();
    }

    /**
     * Sets the Groups in the cache under a given key
     *
     * @param key    - String search value that the Groups are tied to
     * @param groups - Group domain object(s) that needs to be mapped to the key
     */
    public void put(String key, Group... groups) {
        put(key, Arrays.asList(groups));
    }

    /**
     * Sets the Groups in the cache under a given key
     *
     * @param key    - String search value that the Groups are tied to
     * @param groups - Collection of Group domain object(s) that needs to be mapped to the key
     */
    public void put(String key, Collection<Group> groups) {
        if (key == null || key.isEmpty() || groups == null) {
            return;
        }
        CacheNode node = cache.get(key);
        if (node == null && cache.size() >= maxItems) {
            CacheNode oldest = null;
            for (CacheNode n : cache.values()) {
                if (oldest == null || oldest.lastRead.compareTo(n.lastRead) >= 0) {
                    oldest = n;
                }
            }
            cache.remove(oldest.getKey());
        }
        CacheNode newNode = new CacheNode(key, groups);
        cache.put(key, newNode);
    }

    /**
     * Adds a Group to an existing set of Groups tied to the search key
     *
     * @param key   - String search value that the Group is tied to
     * @param group - Group to add to the list of items
     */
    public void addGroup(String key, Group group) {
        if (key == null || group == null) {
            return;
        }
        CacheNode node = cache.get(key);
        if (node != null) {
            node.getGroups().add(group);
        }
    }

    /**
     * Returns the groups tied to the key in the cache
     *
     * @param key - String key value that the Group(s) are mapped to
     * @return - Set of Group domain objects tied to the search key
     */
    public Set<Group> get(String key) {
        CacheNode node = cache.get(key);
        if (node == null) {
            return null;
        }
        node.updateLastRead();
        return new HashSet<Group>(node.getGroups());
    }

    /**
     * Returns the last time the set of Groups tied to the search string was read from the cache.
     * This method will return null of the search string is not in the cache.
     *
     * @param key - String key value that the Group(s) are mapped to
     * @return - Date that the set was last read or null if it does not exist
     */
    public Date getLastReadDate(String key) {
        CacheNode node = cache.get(key);
        return node == null ? null : new Date(node.getLastReadDate().getTime());
    }

    /**
     * This method is protected to allow for testing to prevent creating flaky tests.
     *
     * @param key          - String key value that the Group(s) are mapped to
     * @param lastReadDate - Date to set the for the last read date
     */
    protected void setLastReadDate(String key, Date lastReadDate) {
        cache.get(key).lastRead = lastReadDate;
    }

    private class CacheNode {
        private String key;
        private Date lastRead;
        private Set<Group> groups;

        protected CacheNode(String key, Collection<Group> groups) {
            this.key = key;
            this.groups = new HashSet<Group>();
            for (Group group : groups) {
                this.groups.add(group);
            }
            updateLastRead();
        }

        protected String getKey() {
            return key;
        }

        protected Set<Group> getGroups() {
            return groups;
        }

        protected Date getLastReadDate() {
            return lastRead;
        }

        protected void updateLastRead() {
            lastRead = Calendar.getInstance().getTime();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            CacheNode cacheNode = (CacheNode) o;
            return groups.equals(cacheNode.groups) && key.equals(cacheNode.getKey());
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + groups.hashCode();
            return result;
        }
    }
}
