package com.perlscar.database.impl;

import com.perlscar.database.api.DBLockAccessObject;
import com.perlscar.database.utils.FileDBUtils;
import com.perlscar.exception.EntryNotPresentException;
import com.perlscar.exception.FileDBException;
import com.perlscar.exception.ObjectNotAccessibleException;

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
    public boolean fetchLock(String id, String metadata) throws ObjectNotAccessibleException {
        try {
            if(FileDBUtils.isPresent(file, id)) {
                return false;
            }
            return FileDBUtils.createEntry(file, id, metadata);
        } catch (FileDBException e) {
            e.printStackTrace();
            throw new ObjectNotAccessibleException(e);
        }
    }

    @Override
    public String readLockMetadata(String id) throws ObjectNotAccessibleException, EntryNotPresentException {
        try {
            return FileDBUtils.readEntry(file, id);
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
