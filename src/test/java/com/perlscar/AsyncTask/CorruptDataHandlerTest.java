package com.perlscar.AsyncTask;

import com.google.common.collect.Lists;
import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.api.DBLockAccessObject;
import com.perlscar.database.impl.FileDBAccessObjectImpl;
import com.perlscar.database.impl.FileDBLockAccessObjectImpl;
import com.perlscar.database.model.DataCenter;
import com.perlscar.service.DBSyncServiceImpl;
import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class CorruptDataHandlerTest {

    private String DB_FILENAME1 = "db1.txt";
    private String DB_FILENAME2 = "db2.txt";
    private String LOCK_FILENAME1 = "lock1.txt";
    private String LOCK_FILENAME2 = "lock2.txt";

    private Integer CURRENT_DC = 0;
    private DBAccessObject dbSyncService;
    private List<DataCenter> dataCenters;
    private CorruptDataHandler corruptDataHandler;

    @Before
    public void setUp() throws Exception {

        dataCenters = Lists.newArrayList();
        dataCenters.add(new DataCenter(new FileDBAccessObjectImpl(DB_FILENAME1),
                new FileDBLockAccessObjectImpl(LOCK_FILENAME1)));
        dataCenters.add(new DataCenter(new FileDBAccessObjectImpl(DB_FILENAME2),
                new FileDBLockAccessObjectImpl(LOCK_FILENAME2)));
        dbSyncService = new DBSyncServiceImpl(dataCenters, CURRENT_DC);
        corruptDataHandler = new CorruptDataHandler(dataCenters, CURRENT_DC);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(new File(DB_FILENAME1));
        FileUtils.forceDelete(new File(DB_FILENAME2));
        FileUtils.forceDelete(new File(LOCK_FILENAME1));
        FileUtils.forceDelete(new File(LOCK_FILENAME2));
    }


    @Test
    public void testLockClearingWithoutSync() throws Exception {
        String id1 = "id1";
        String val = CURRENT_DC.toString();

        DataCenter currentDC = dataCenters.get(CURRENT_DC);
        DBLockAccessObject dbLockAccessObject = currentDC.getDbLockAccessObject();
        boolean lockFetched = dbLockAccessObject.fetchLock(id1, val);
        Assert.assertTrue(lockFetched);

        corruptDataHandler.run();

        List<String> entries = currentDC.getDbLockAccessObject().getAllEntries();
        Assert.assertTrue(entries.isEmpty());
    }
}