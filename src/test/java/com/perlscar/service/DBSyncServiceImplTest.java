package com.perlscar.service;

import com.google.common.collect.Lists;
import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.impl.FileDBAccessObjectImpl;
import com.perlscar.database.impl.FileDBLockAccessObjectImpl;
import com.perlscar.database.model.DataCenter;
import com.perlscar.exception.ObjectNotAccessibleException;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DBSyncServiceImplTest {

    private String DB_FILENAME1 = "db1.txt";
    private String DB_FILENAME2 = "db2.txt";
    private String LOCK_FILENAME1 = "lock1.txt";
    private String LOCK_FILENAME2 = "lock2.txt";

    private Integer CURRENT_DC = 0;
    private DBAccessObject dbSyncService;
    private List<DataCenter> dataCenters;

    @Before
    public void setUp() throws Exception {

        dataCenters = Lists.newArrayList();
        dataCenters.add(new DataCenter(new FileDBAccessObjectImpl(DB_FILENAME1),
                        new FileDBLockAccessObjectImpl(LOCK_FILENAME1)));
        dataCenters.add(new DataCenter(new FileDBAccessObjectImpl(DB_FILENAME2),
                new FileDBLockAccessObjectImpl(LOCK_FILENAME2)));
        dbSyncService = new DBSyncServiceImpl(dataCenters, CURRENT_DC);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(new File(DB_FILENAME1));
        FileUtils.forceDelete(new File(DB_FILENAME2));
        FileUtils.forceDelete(new File(LOCK_FILENAME1));
        FileUtils.forceDelete(new File(LOCK_FILENAME2));
    }

    @Test(expected = ObjectNotAccessibleException.class)
    public void testBlockerGet() throws Exception {
        String id = "id1";
        String val = "val";

        boolean lockFetched = dataCenters.get(CURRENT_DC).getDbLockAccessObject().fetchLock(id, val);
        Assert.assertTrue(lockFetched);

        dbSyncService.getById(id);
    }

    @Test(expected = ObjectNotAccessibleException.class)
    public void testBlockerUpdate() throws Exception {
        String id = "id1";
        String val = "val";

        boolean lockFetched = dataCenters.get(CURRENT_DC).getDbLockAccessObject().fetchLock(id, val);
        Assert.assertTrue(lockFetched);

        String val2 = "val2";
        dbSyncService.updateEntry(id, val2);
    }

}