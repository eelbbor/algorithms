package com.pexercise.service;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

@Test
public class SearchServiceTest {
    private static final String DUMMY_TEST_DIRECTORY_PATH = "./test/resources/";
    private File testDirectory;
    private File dummyFile;

    @BeforeClass
    protected void classSetUp() {
        testDirectory = new File(DUMMY_TEST_DIRECTORY_PATH);
        if (!testDirectory.exists()) {
            testDirectory.mkdir();
            testDirectory.deleteOnExit();
        }
    }

    @BeforeMethod
    protected void methodSetUp() {
        try {
            dummyFile = File.createTempFile("test_", "_dummy.tsv", testDirectory);
            dummyFile.deleteOnExit();
            writeLineToDummyFile("name", "id", "member");
        } catch (IOException e) {
            fail("Unable to initialize test");
        }
    }

    @AfterMethod
    protected void methodTearDown() {
        dummyFile.delete();
    }

    public void shouldHonorIndexFileLimit() {
        List<String> groupNameArray = Arrays.asList("foo", "man", "choo", "says", "me", "and", "fred");
        Set<String> groups = new TreeSet<String>(groupNameArray);
        for(String groupName : groups) {
            writeLineToDummyFile(groupName, "/" + groupName);
        }
        SearchService service = new SearchService(dummyFile.getPath(), 5, 5);
        assertEquals(service.getShardCount(), 5);
        Map<String, Integer> fileCountMap = getFileSearchStringCountMap(service, groups);
        assertEquals(fileCountMap.size(), 5);
        for(String key : fileCountMap.keySet()) {
            if(key.endsWith("_0") || key.endsWith("_1")) {
                assertEquals(fileCountMap.get(key).intValue(),2);
            } else {
                assertEquals(fileCountMap.get(key).intValue(),1);
            }
        }
    }

    public void shouldReturnEmptyStringForNullSearchString() {
        List<String> groupNameArray = Arrays.asList("foo", "man", "choo", "says", "me", "and", "fred");
        Set<String> groups = new TreeSet<String>(groupNameArray);
        for(String groupName : groups) {
            writeLineToDummyFile(groupName, "/" + groupName);
        }
        SearchService service = new SearchService(dummyFile.getPath(), 1, 5);
        assertTrue(service.query(null).isEmpty());
    }

    public void shouldReturnEmptyStringForEmptySearchString() {
        List<String> groupNameArray = Arrays.asList("foo", "man", "choo", "says", "me", "and", "fred");
        Set<String> groups = new TreeSet<String>(groupNameArray);
        for(String groupName : groups) {
            writeLineToDummyFile(groupName, "/" + groupName);
        }
        SearchService service = new SearchService(dummyFile.getPath(), 1, 5);
        assertTrue(service.query("").isEmpty());
    }

    public void shouldReturnEmptyStringForInvalidSearchString() {
        List<String> groupNameArray = Arrays.asList("foo", "man", "choo", "says", "me", "and", "fred");
        Set<String> groups = new TreeSet<String>(groupNameArray);
        for(String groupName : groups) {
            writeLineToDummyFile(groupName, "/" + groupName);
        }
        SearchService service = new SearchService(dummyFile.getPath(), 1, 5);
        assertTrue(service.query("blah").isEmpty());
    }

    public void shouldReturnUrlForSearchString() {
        List<String> groupNameArray = Arrays.asList("foo", "man", "choo", "says", "me", "and", "fred");
        Set<String> groups = new TreeSet<String>(groupNameArray);
        for(String groupName : groups) {
            writeLineToDummyFile(groupName, "/" + groupName);
        }
        SearchService service = new SearchService(dummyFile.getPath(), 1, 5);
        for(String groupName : groups) {
            assertEquals(service.query(groupName), "/" + groupName);
        }
    }

    public void shouldReturnCommaSeparatedUrlListForMultipleEntries() {
        writeLineToDummyFile("foo man choo", "/foo1");
        writeLineToDummyFile("sam i am", "/sam1");
        writeLineToDummyFile("foo man choo", "/foo2");
        writeLineToDummyFile("and the word is", "/and");
        SearchService service = new SearchService(dummyFile.getPath(), 2, 5);
        assertEquals(service.query("foo man choo"), "/foo1,/foo2");
    }

    public void shouldSatisfyQueryFromCache() {
        writeLineToDummyFile("foo man choo", "/foo1");
        writeLineToDummyFile("sam i am", "/sam1");
        writeLineToDummyFile("foo man choo", "/foo2");
        writeLineToDummyFile("and the word is", "/and");
        SearchService service = new SearchService(dummyFile.getPath(), 2, 5);
        assertEquals(service.query("foo man choo"), "/foo1,/foo2");
        assertEquals(service.getQueryCount(),1);
        assertEquals(service.getCacheHit(),1);
    }

    public void shouldSatisfyQueryFromFile() {
        writeLineToDummyFile("foo man choo", "/foo1");
        writeLineToDummyFile("sam i am", "/sam1");
        writeLineToDummyFile("foo man choo", "/foo2");
        writeLineToDummyFile("and the word is", "/and");
        SearchService service = new SearchService(dummyFile.getPath(), 2, 5);
        assertEquals(service.query("sam i am"), "/sam1");
        assertEquals(service.getQueryCount(),1);
        assertEquals(service.getCacheHit(), 0);
    }

    private Map<String, Integer> getFileSearchStringCountMap(SearchService service, Set<String> searchStrings) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String string : searchStrings) {
            String file = service.getShardNameForSearchString(string);
            if(file != null) {
                if(map.get(file) == null) {
                    map.put(file, 0);
                }
                map.put(file, map.get(file) + 1);
            }
        }
        return map;
    }

    private void writeLineToDummyFile(String group, String id) {
        writeLineToDummyFile(group, id, "");
    }

    private void writeLineToDummyFile(String group, String id, String member) {
        try {
            FileOutputStream fos = new FileOutputStream(dummyFile, true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            PrintWriter pw = new PrintWriter(bw);
            pw.println(String.format("%s\t%s\t%s", group, id, member));
            pw.close();
        } catch (IOException e) {
            fail("Unable to write to dummy file");
        }
    }
}