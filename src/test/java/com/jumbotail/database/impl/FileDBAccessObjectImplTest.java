package com.jumbotail.database.impl;

import com.jumbotail.database.api.DBAccessObject;
import com.jumbotail.exception.EntryNotPresentException;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileDBAccessObjectImplTest {

    private static final String FILENAME = "db.txt";
    private DBAccessObject fileDBAccessObject;

    @Before
    public void beforeMethod() throws IOException {
        fileDBAccessObject = new FileDBAccessObjectImpl(FILENAME);
    }

    @After
    public void afterMethod() {
        try {
            FileUtils.forceDelete(new File(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = EntryNotPresentException.class)
    public void testFalseScenario() throws Exception {
        fileDBAccessObject.getById("id");
    }

    @Test
    public void testFileDB() throws Exception {
        String id1 = "id1";
        String id2 = "id2";

        String value1 = "value1";
        String value11 = "value11";
        String value2 = "value2";

        boolean isSuccessful = fileDBAccessObject.createEntry(id1, value1);
        Assert.assertTrue(isSuccessful);
        isSuccessful = fileDBAccessObject.createEntry(id2, value2);
        Assert.assertTrue(isSuccessful);

        String value = fileDBAccessObject.getById(id1);
        Assert.assertEquals(value, value1);
        value = fileDBAccessObject.getById(id2);
        Assert.assertEquals(value, value2);

        isSuccessful = fileDBAccessObject.updateEntry(id1, value11);
        Assert.assertTrue(isSuccessful);

        value = fileDBAccessObject.getById(id1);
        Assert.assertEquals(value, value11);
        value = fileDBAccessObject.getById(id2);
        Assert.assertEquals(value, value2);
        
        isSuccessful = fileDBAccessObject.createEntry(id1, value1);
        Assert.assertFalse(isSuccessful);

        isSuccessful = fileDBAccessObject.deleteEntry(id1);
        Assert.assertTrue(isSuccessful);

        isSuccessful = fileDBAccessObject.createEntry(id1, value11);
        Assert.assertTrue(isSuccessful);
        value = fileDBAccessObject.getById(id1);
        Assert.assertEquals(value, value11);
        value = fileDBAccessObject.getById(id2);
        Assert.assertEquals(value, value2);

    }
}