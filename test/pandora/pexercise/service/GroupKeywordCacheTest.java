package com.pexercise.service;

import com.pexercise.domain.Group;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

@Test
public class GroupKeywordCacheTest {
    private static final int MAX_ITEMS = 5;
    private GroupKeywordCache cache;

    @BeforeMethod
    protected void setUp() {
        cache = new GroupKeywordCache(MAX_ITEMS);
    }


    public void shouldHaveEmptyCache() {
        assertEquals(cache.size(), 0);
    }

    public void shouldAddItemsToCache() {
        Set<String> urls = new TreeSet<String>(Arrays.asList("url1", "url2"));
        cache.put("foo", new Group("foo man choo", "url1", "url2"));
        cache.put("man", new Group("foo man choo", urls));
        cache.put("choo", new Group("foo man choo", "url1", "url2"));
        assertEquals(cache.size(), 3);

        validateCacheNode("foo", 1, urls);
        validateCacheNode("man", 1, urls);
        validateCacheNode("choo", 1, urls);
    }

    public void shouldNotEffectCacheIfTryToPutWithNullKey() {
        cache.put("foo", new Group("foo man choo", "url1", "url2"));
        cache.put(null, new Group("foo man choo", "url1", "url2"));
        assertEquals(cache.size(), 1);
    }

    public void shouldNotEffectCacheIfTryToPutWithEmptyKey() {
        cache.put("foo", new Group("foo man choo", "url1", "url2"));
        cache.put("", new Group("foo man choo", "url1", "url2"));
        assertEquals(cache.size(), 1);
    }

    public void shouldHonorCacheSizeLimit() {
        for(int i = 0 ; i <= MAX_ITEMS ; i++) {
            cache.put("foo" + i, new Group("foo" + i,"url" + i));
            threadSleep(5);
        }
        assertEquals(cache.size(), MAX_ITEMS);
        assertNull(cache.get("foo0"));

        for(int i = 1 ; i <= MAX_ITEMS ; i++) {
            assertNotNull(cache.get("foo" + i));
        }
    }

    public void shouldHonorCacheSizeLimitRemoveInitialIfTimeStampsIdentical() {
        Date date = Calendar.getInstance().getTime();
        for(int i = MAX_ITEMS ; i >= 0 ; i--) {
            String key = "foo" + i;
            cache.put(key, new Group(key,"url" + i));
            cache.setLastReadDate(key, date);
        }
        assertEquals(cache.size(), MAX_ITEMS);
        assertNull(cache.get("foo" + MAX_ITEMS));

        for(int i = 0 ; i < MAX_ITEMS ; i++) {
            assertNotNull(cache.get("foo" + i));
        }
    }

    public void shouldAddGroupToSearchKeySet() {
        cache.put("foo", new Group("foo man", "url1"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);

        cache.addGroup("foo", new Group("foo choo", "url2"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 2);
    }

    public void shouldNotEffectGroupsIfAddGroupWithNullKey() {
        cache.put("foo", new Group("foo man", "url1"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);

        cache.addGroup(null, new Group("foo choo", "url2"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);
    }

    public void shouldNotEffectGroupsIfAddGroupWithEmptyKey() {
        cache.put("foo", new Group("foo man", "url1"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);

        cache.addGroup("", new Group("foo choo", "url2"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);
    }

    public void shouldNotEffectGroupsIfAddGroupWithNullGroup() {
        cache.put("foo", new Group("foo man", "url1"));
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);

        cache.addGroup("foo", null);
        assertEquals(cache.size(), 1);
        assertEquals(cache.get("foo").size(), 1);
    }

    public void shouldReturnNullForInvalidItem() {
        cache.put("foo", new Group("foo man choo", "url1", "url2"));
        assertNull(cache.get("man"));
    }

    public void shouldUpdateNodeLastReadDateOnGet() {
        cache.put("foo", new Group("foo man choo", "url1", "url2"));
        Date initialDate = cache.getLastReadDate("foo");
        threadSleep(5);
        cache.get("foo");
        assertTrue(initialDate.compareTo(cache.getLastReadDate("foo")) < 0);
    }

    private void threadSleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shouldReturnNullForReadDateOfInvalidSearchString() {
        assertNull(cache.getLastReadDate("fred"));
    }

    private void validateCacheNode(String key, int groupSize ,Set<String> urls) {
        Set<Group> group = cache.get(key);
        assertEquals(group.size(), groupSize);
        assertTrue(((Group) group.toArray()[0]).getUrls().containsAll(urls));
    }
}