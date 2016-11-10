package com.jumbotail.service;

import com.jumbotail.database.api.DBAccessObject;
import com.jumbotail.database.model.DataCenter;
import com.jumbotail.exception.EntryNotPresentException;
import com.jumbotail.exception.ObjectNotAccessibleException;

import java.util.List;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class DBSyncServiceImpl implements DBAccessObject {

    private final List<DataCenter> dataCenters;

    public DBSyncServiceImpl(List<DataCenter> dataCenters) {
        this.dataCenters = dataCenters;
    }

    @Override
    public String getById(String id) throws ObjectNotAccessibleException, EntryNotPresentException {
        return null;
    }

    @Override
    public boolean createEntry(String id, String object) throws ObjectNotAccessibleException {
        return false;
    }

    @Override
    public boolean updateEntry(String id, String object) throws ObjectNotAccessibleException {
        return false;
    }

    @Override
    public boolean deleteEntry(String id) throws ObjectNotAccessibleException {
        return false;
    }
}
