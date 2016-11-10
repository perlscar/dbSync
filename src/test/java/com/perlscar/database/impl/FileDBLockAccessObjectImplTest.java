package com.perlscar.database.impl;

import com.perlscar.database.api.DBLockAccessObject;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class FileDBLockAccessObjectImplTest {

    private static final String FILENAME = "lock.txt";
    private DBLockAccessObject fileDBLockAccessObject;

    @Before
    public void beforeMethod() throws IOException {
        fileDBLockAccessObject = new FileDBLockAccessObjectImpl(FILENAME);
    }

    @After
    public void afterMethod() {
        try {
            FileUtils.forceDelete(new File(FILENAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsLocked() throws Exception {
        String id1 = "id1";
        String id2 = "id2";

        boolean isLocked = fileDBLockAccessObject.isLocked(id1);
        Assert.assertFalse(isLocked);

        boolean isLockReleased = fileDBLockAccessObject.releaseLock(id1);
        Assert.assertTrue(isLockReleased);

        isLocked = fileDBLockAccessObject.isLocked(id1);
        Assert.assertFalse(isLocked);

        boolean isLockFetched = fileDBLockAccessObject.fetchLock(id1);
        Assert.assertTrue(isLockFetched);

        isLocked = fileDBLockAccessObject.isLocked(id1);
        Assert.assertTrue(isLocked);

        boolean isLock2Fetched = fileDBLockAccessObject.fetchLock(id2);
        Assert.assertTrue(isLock2Fetched);

        isLockReleased = fileDBLockAccessObject.releaseLock(id1);
        Assert.assertTrue(isLockReleased);

        isLocked = fileDBLockAccessObject.isLocked(id1);
        Assert.assertFalse(isLocked);
    }
}