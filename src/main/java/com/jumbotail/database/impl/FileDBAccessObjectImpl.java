package com.jumbotail.database.impl;

import com.jumbotail.database.api.DBAccessObject;
import com.jumbotail.database.utils.FileDBUtils;
import com.jumbotail.exception.EntryNotPresentException;
import com.jumbotail.exception.FileDBException;
import com.jumbotail.exception.ObjectNotAccessibleException;

import java.io.File;
import java.io.IOException;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBAccessObjectImpl implements DBAccessObject {
    private final File file;

    public FileDBAccessObjectImpl(String filename) throws IOException {
        file = new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    @Override
    public String getById(String id) throws ObjectNotAccessibleException, EntryNotPresentException {
        try {
            return FileDBUtils.readEntry(file, id);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        }
    }

    @Override
    public boolean createEntry(String id, String object) throws ObjectNotAccessibleException {
        try {
            if(FileDBUtils.isPresent(file, id)) {
                return false;
            }
            return FileDBUtils.createEntry(file, id, object);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        }
    }

    @Override
    public boolean updateEntry(String id, String object) throws ObjectNotAccessibleException {
        try {
            FileDBUtils.dropEntry(file, id);
            return FileDBUtils.createEntry(file, id, object);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        } catch (EntryNotPresentException e) {
            return false;
        }
    }

    @Override
    public boolean deleteEntry(String id) throws ObjectNotAccessibleException {
        try {
            return FileDBUtils.dropEntry(file, id);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        } catch (EntryNotPresentException e) {
            return false;
        }
    }
}
