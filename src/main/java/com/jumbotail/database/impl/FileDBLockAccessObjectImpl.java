package com.jumbotail.database.impl;

import com.jumbotail.database.api.DBLockAccessObject;
import com.jumbotail.database.utils.FileDBUtils;
import com.jumbotail.exception.EntryNotPresentException;
import com.jumbotail.exception.FileDBException;
import com.jumbotail.exception.ObjectNotAccessibleException;

import java.io.File;
import java.io.IOException;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBLockAccessObjectImpl implements DBLockAccessObject {

    private final File file;

    public FileDBLockAccessObjectImpl(String filename) throws IOException {
        file = new File(filename);
        if(!file.exists()){
            file.createNewFile();
        }
    }

    @Override
    public boolean isLocked(String id) throws ObjectNotAccessibleException {
        try {
            return FileDBUtils.isPresent(file, id);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        }
    }

    @Override
    public boolean fetchLock(String id) throws ObjectNotAccessibleException {
        try {
             if(FileDBUtils.isPresent(file, id)) {
                 return false;
             }
            return FileDBUtils.createEntry(file, id);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        }
    }

    @Override
    public boolean releaseLock(String id) throws ObjectNotAccessibleException {
        try {
            return FileDBUtils.dropEntry(file, id);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        } catch (EntryNotPresentException e) {
            //e.printStackTrace();
            return true;
        }
    }
}
