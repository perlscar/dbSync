package com.jumbotail.database.impl;

import com.jumbotail.database.api.DBAccessObject;
import com.jumbotail.contract.EntryCreationResponse;
import com.jumbotail.contract.EntryDeletionResponse;
import com.jumbotail.contract.EntryUpdationResponse;
import com.jumbotail.exception.ObjectNotAccessibleException;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBAccessObjectImpl implements DBAccessObject {
    @Override
    public Object getById(String id) throws ObjectNotAccessibleException {
        return null;
    }

    @Override
    public EntryCreationResponse createEntry(String id, String object) throws ObjectNotAccessibleException {
        return null;
    }

    @Override
    public EntryUpdationResponse updateEntry(String id, String object) throws ObjectNotAccessibleException {
        return null;
    }

    @Override
    public EntryDeletionResponse deleteEntry(String id, String object) throws ObjectNotAccessibleException {
        return null;
    }
}
