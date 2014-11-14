package com.pexercise.domain;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class GroupTest {
    public void shouldCreateGroupWithCorrectName() {
        Group group = new Group("foo", "url1");
        assertEquals(group.getName(), "foo");
    }

    public void shouldCreateGroupWithSingleUrl() {
        Group group = new Group("foo", "url1");
        validateUrlList(group, Arrays.asList("url1"));
    }

    public void shouldCreateGroupWithMultipleUrls() {
        Group group = new Group("foo", "url1", "url2");
        validateUrlList(group, Arrays.asList("url1", "url2"));
    }

    public void shouldCreateGroupWithUrlCollection() {
        Collection<String> urls = Arrays.asList("url1", "url2");
        Group group = new Group("foo", urls);
        validateUrlList(group, urls);
    }

    public void shouldThrowExceptionForNullName() {
        try {
            new Group(null, "url1");
            fail("should have thrown exception for null name");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForEmptyName() {
        try {
            new Group("", "url1");
            fail("should have thrown exception for null name");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForNullUrls() {
        try {
            new Group("foo", "url1", null);
            fail("should have thrown exception for null url");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForEmptyUrls() {
        try {
            new Group("foo", "url1", "");
            fail("should have thrown exception for empty url");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForEmptyUrlCollection() {
        try {
            new Group("foo", new ArrayList<String>());
            fail("should have thrown exception for empty url collection");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldAddUrlToGroup() {
        Group group = new Group("foo", "url1");
        validateUrlList(group, Arrays.asList("url1"));

        group.addUrl("url2");
        validateUrlList(group, Arrays.asList("url1", "url2"));
    }

    public void shouldThrowExceptionIfTryingToAddNullUrlToGroup() {
        Group group = new Group("foo", "url1");
        try {
            group.addUrl(null);
            fail("should have thrown exception for null url");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionIfTryingToAddEmptyUrlToGroup() {
        Group group = new Group("foo", "url1");
        try {
            group.addUrl("");
            fail("should have thrown exception for empty url");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldReplaceUrl() {
        Collection<String> urls = Arrays.asList("url1", "url2");
        Group group = new Group("foo", urls);
        validateUrlList(group, urls);

        group.replaceUrl("url1", "url3");
        validateUrlList(group, Arrays.asList("url3", "url2"));
    }

    public void shouldAddUrlIfReplaceOneThatDoesNotExist() {
        Group group = new Group("foo", "url1");
        group.replaceUrl("url2", "url3");
        validateUrlList(group, Arrays.asList("url1", "url3"));
    }

    public void shouldThrowExceptionForNullReplacementUrl() {
        try {
            Group group = new Group("foo", "url1");
            group.replaceUrl("url1", null);
            fail("should have throw exception for null replacement value");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionForEmptyReplacementUrl() {
        try {
            Group group = new Group("foo", "url1");
            group.replaceUrl("url1", "");
            fail("should have throw exception for empty replacement value");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldThrowExceptionIfTryToReplaceSoEmptyUrlSet() {
        try {
            Group group = new Group("foo", "url1");
            group.replaceUrl("url1", "url1");
            fail("should have thrown exception trying to remove the last url via replace");
        } catch (IllegalArgumentException e) {
        }
    }

    public void shouldRemoveUrl() {
        Collection<String> urls = Arrays.asList("url1", "url2");
        Group group = new Group("foo", urls);
        validateUrlList(group, urls);

        group.removeUrl("url2");
        validateUrlList(group, Arrays.asList("url1"));
    }

    public void shouldNotRemoveInvalidUrl() {
        Collection<String> urls = Arrays.asList("url1", "url2");
        Group group = new Group("foo", urls);
        validateUrlList(group, urls);

        group.removeUrl("url3");
        validateUrlList(group, urls);
    }

    public void shouldThrowExceptionIfTryToRemoveTheLastUrl() {
        try {
            Group group = new Group("foo", "url1");
            group.removeUrl("url1");
            fail("should have thrown exception trying to remove the last url");
        } catch (IllegalArgumentException e) {
        }
    }

    private void validateUrlList(Group group, Collection<String> expectedUrls) {
        Collection<String> actualUrls = group.getUrls();
        assertEquals(actualUrls.size(), expectedUrls.size());
        assertTrue(actualUrls.containsAll(expectedUrls));
    }

}