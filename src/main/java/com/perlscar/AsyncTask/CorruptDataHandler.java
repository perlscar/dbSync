package com.perlscar.AsyncTask;

import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.api.DBLockAccessObject;
import com.perlscar.database.model.DataCenter;
import com.perlscar.exception.EntryNotPresentException;
import com.perlscar.exception.ObjectNotAccessibleException;

import java.util.List;

/**
 * Created by achit.ojha on 11/11/16.
 */
public class CorruptDataHandler {

    private final List<DataCenter> dataCenters;
    private final Integer currentDC;

    public CorruptDataHandler(List<DataCenter> dataCenters, Integer currentDC) throws Exception {
        if(currentDC >= dataCenters.size()) {
            throw new Exception("Incorrect Parameters");
        }
        this.dataCenters = dataCenters;
        this.currentDC = currentDC;
    }

    public void run() throws ObjectNotAccessibleException {
        DBLockAccessObject currentDbLockAccessObject = dataCenters.get(currentDC).getDbLockAccessObject();
        List<String> entries = currentDbLockAccessObject.getAllEntries();
        for(String entry : entries) {
            try {
                String metadata = currentDbLockAccessObject.readLockMetadata(entry);
                Integer masterDC = Integer.parseInt(metadata);
                DBAccessObject currentDbAccessObject = dataCenters.get(currentDC).getDbAccessObject();
                DBAccessObject masterDbAccessObject = dataCenters.get(masterDC).getDbAccessObject();

                if(synchronizeWithMaster(entry, currentDbAccessObject, masterDbAccessObject)) {
                    currentDbLockAccessObject.releaseLock(entry);
                }
            } catch (EntryNotPresentException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean synchronizeWithMaster(String entry, DBAccessObject currentDbAccessObject, DBAccessObject masterDbAccessObject)
            throws ObjectNotAccessibleException {
        String currentData = fetchData(currentDbAccessObject, entry);
        String masterData = fetchData(masterDbAccessObject, entry);

        if(currentData == null && masterData == null) {
            return true;
        }

        if(currentData == null && masterData != null) {
            return currentDbAccessObject.createEntry(entry, masterData);
        }

        if(masterData == null && currentData != null) {
            return currentDbAccessObject.deleteEntry(entry);
        }

        return currentDbAccessObject.updateEntry(entry, masterData);
    }

    private String fetchData(DBAccessObject dbAccessObject, String id) {
        try {
            dbAccessObject.getById(id);
        } catch (ObjectNotAccessibleException e) {
            e.printStackTrace();
        } catch (EntryNotPresentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
