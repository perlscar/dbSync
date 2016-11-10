package com.perlscar.database.utils;

import com.perlscar.exception.EntryNotPresentException;
import com.perlscar.exception.FileDBException;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileDBUtilsTest {


    private File file;
    private static final String FILENAME = "db.txt";

    @Before
    public void beforeMethod() throws IOException {
        file = new File(FILENAME);
        file.createNewFile();
    }

    @After
    public void afterMethod() {
        try {
            FileUtils.forceDelete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEntries() throws Exception {
        String entry1 = "entry1";
        String entry2 = "entry2";
        String entry3 = "entry3";
        String entry4 = "entry4";
        String entry5 = "entry5";

        boolean operationSuccessful = FileDBUtils.createEntry(file, entry1);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.createEntry(file, entry2);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.createEntry(file, entry3);
        Assert.assertTrue(operationSuccessful);

        boolean isPresent = FileDBUtils.isPresent(file, entry1);
        Assert.assertTrue(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry2);
        Assert.assertTrue(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry4);
        Assert.assertFalse(isPresent);

        operationSuccessful = FileDBUtils.dropEntry(file, entry1);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.dropEntry(file, entry3);
        Assert.assertTrue(operationSuccessful);

        operationSuccessful = FileDBUtils.createEntry(file, entry5);
        Assert.assertTrue(operationSuccessful);

        isPresent = FileDBUtils.isPresent(file, entry1);
        Assert.assertFalse(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry2);
        Assert.assertTrue(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry3);
        Assert.assertFalse(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry4);
        Assert.assertFalse(isPresent);
        isPresent = FileDBUtils.isPresent(file, entry5);
        Assert.assertTrue(isPresent);

    }

    @Test(expected = EntryNotPresentException.class)
    public void testException() throws FileDBException, EntryNotPresentException {
        String entry1 = "entry1";
        FileDBUtils.dropEntry(file, entry1);
    }

    @Test
    public void testEntryValues() throws Exception {
        String entry1 = "entry1";
        String entry2 = "entry2";
        String entry3 = "entry3";
        String entry4 = "entry4";
        String entry5 = "entry5";

        String value1 = "value1";
        String value2 = "value2";
        String value3 = "value3";
        String value4 = "value4";
        String value5 = "value5";

        boolean operationSuccessful = FileDBUtils.createEntry(file, entry1, value1);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.createEntry(file, entry2, value2);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.createEntry(file, entry3, value3);
        Assert.assertTrue(operationSuccessful);

        boolean isPresent = FileDBUtils.isPresent(file, entry1);
        String value = FileDBUtils.readEntry(file, entry1);
        Assert.assertTrue(isPresent);
        Assert.assertEquals(value1, value);

        isPresent = FileDBUtils.isPresent(file, entry2);
        value = FileDBUtils.readEntry(file, entry2);
        Assert.assertTrue(isPresent);
        Assert.assertEquals(value2, value);

        isPresent = FileDBUtils.isPresent(file, entry4);
        Assert.assertFalse(isPresent);

        operationSuccessful = FileDBUtils.dropEntry(file, entry1);
        Assert.assertTrue(operationSuccessful);
        operationSuccessful = FileDBUtils.dropEntry(file, entry3);
        Assert.assertTrue(operationSuccessful);

        operationSuccessful = FileDBUtils.createEntry(file, entry5, value5);
        Assert.assertTrue(operationSuccessful);

        isPresent = FileDBUtils.isPresent(file, entry1);
        Assert.assertFalse(isPresent);

        isPresent = FileDBUtils.isPresent(file, entry2);
        value = FileDBUtils.readEntry(file, entry2);
        Assert.assertTrue(isPresent);
        Assert.assertEquals(value2, value);

        isPresent = FileDBUtils.isPresent(file, entry3);
        Assert.assertFalse(isPresent);

        isPresent = FileDBUtils.isPresent(file, entry4);
        Assert.assertFalse(isPresent);

        isPresent = FileDBUtils.isPresent(file, entry5);
        value = FileDBUtils.readEntry(file, entry5);
        Assert.assertTrue(isPresent);
        Assert.assertEquals(value5, value);

        List<String> entries = FileDBUtils.getEntries(file);
        Assert.assertEquals(2, entries.size());
    }
}