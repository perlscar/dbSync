package com.perlscar.service;

import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.model.DataCenter;
import com.perlscar.exception.EntryNotPresentException;
import com.perlscar.exception.ObjectNotAccessibleException;

import java.util.List;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class DBSyncServiceImpl implements DBAccessObject {

    private final List<DataCenter> dataCenters;
    private final Integer currentDC;

    public DBSyncServiceImpl(List<DataCenter> dataCenters, Integer currentDC) throws Exception {
        if(currentDC >= dataCenters.size()) {
            throw new Exception("Incorrect Parameters");
        }
        this.dataCenters = dataCenters;
        this.currentDC = currentDC;
    }

    @Override
    public String getById(String id) throws ObjectNotAccessibleException, EntryNotPresentException {
        for(DataCenter dataCenter: dataCenters) {
            if(dataCenter.getDbLockAccessObject().isLocked(id)) {
                throw new ObjectNotAccessibleException("Object Locked");
            }
        }

        return dataCenters.get(currentDC).getDbAccessObject().getById(id);
    }

    @Override
    public boolean createEntry(String id, String object) throws ObjectNotAccessibleException {

        fetchAllLocks(id);

        DBAccessObject dbAccessObject = dataCenters.get(currentDC).getDbAccessObject();
        if(!dbAccessObject.createEntry(id, object)) {
            return false;
        }

        releaseAllLocks(id);
        return true;
    }

    @Override
    public boolean updateEntry(String id, String object) throws ObjectNotAccessibleException {
        fetchAllLocks(id);

        DBAccessObject dbAccessObject = dataCenters.get(currentDC).getDbAccessObject();
        if(!dbAccessObject.updateEntry(id, object)) {
            return false;
        }

        releaseAllLocks(id);
        return true;
    }

    @Override
    public boolean deleteEntry(String id) throws ObjectNotAccessibleException {
        fetchAllLocks(id);

        DBAccessObject dbAccessObject = dataCenters.get(currentDC).getDbAccessObject();
        if(!dbAccessObject.deleteEntry(id)) {
            return false;
        }

        releaseAllLocks(id);
        return true;
    }

    private void releaseAllLocks(String id) throws ObjectNotAccessibleException {
        for(DataCenter dataCenter: dataCenters) {
            if(!dataCenter.getDbLockAccessObject().releaseLock(id)) {
                throw new ObjectNotAccessibleException("Couldn't retrieve lock");
            }
        }
    }

    private void fetchAllLocks(String id) throws ObjectNotAccessibleException {
        for(DataCenter dataCenter: dataCenters) {
            if(!dataCenter.getDbLockAccessObject().fetchLock(id, currentDC.toString())) {
                throw new ObjectNotAccessibleException("Couldn't retrieve lock");
            }
        }
    }
}
