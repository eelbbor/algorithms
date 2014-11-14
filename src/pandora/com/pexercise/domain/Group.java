package com.pexercise.domain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * The Group domain object tracks the name of a Group and the various urls tied to the Group
 */
public class Group {
    private String name;
    private Set<String> urls;

    /**
     * Constructor to build a Group with a variable number of URLs
     * @param name - String Group name
     * @param urls - String url or set of urls associated with the group
     */
    public Group(String name, String... urls) {
        this(name, Arrays.asList(urls));
    }

    /**
     * Constructor to build a Group with a Collection of URLs
     * @param name - String Group name
     * @param urls - Collection of URLs associated with the Group
     */
    public Group(String name, Collection<String> urls) {
        //Should likely do this validation prior to being constructed, but for simplicity sake starting with it here
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name for a Group cannot be null or empty");
        }
        if(urls == null || urls.size() < 1) {
            throw new IllegalArgumentException("The URLs for a Group must contain at least one url");
        }
        this.name = name;
        this.urls = new TreeSet<String>();
        for(String url : urls) {
            addUrl(url);
        }
    }

    public String getName() {
        return name;
    }

    /**
     * Adds a collection of URLs to be associated with the Group.  See addUrl method for
     * restrictions on values.
     * @param urls - Collection of string value URLs to be added to the object
     */
    public void addUrls(Collection<String> urls) {
        for(String url : urls) {
            addUrl(url);
        }
    }

    /**
     * Add a URL to be associated with the group.  NOTE: The values cannot be null nor empty.
     * @param url - String representation of the URL
     */
    public void addUrl(String url) {
        if(url == null || url.isEmpty()) {
            throw new IllegalArgumentException("Any URL for a Group cannot be null or empty");
        }
        urls.add(url);
    }

    /**
     * Replaces a URL with a new one for the Group
     * @param oldUrl - String old URL to be replaced
     * @param newUrl - String new URL to replace with
     */
    public void replaceUrl(String oldUrl, String newUrl)  {
        addUrl(newUrl);
        removeUrl(oldUrl);
    }

    /**
     * Removes a URL from those associated with a given group.  Note you cannot remove
     * the last url from the list
     * @param url - URL to disassociate
     */
    public void removeUrl(String url) {
        if(urls.size() == 1 && urls.contains(url)) {
            throw new IllegalArgumentException("A Group must have at least one URL associated with it");
        }
        urls.remove(url);
    }

    public Set<String> getUrls() {
        return new TreeSet<String>(urls);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Group group = (Group) o;

        return name.equals(group.name) && urls.equals(group.urls);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + urls.hashCode();
        return result;
    }
}
