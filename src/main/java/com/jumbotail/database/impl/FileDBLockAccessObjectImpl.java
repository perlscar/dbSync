package com.jumbotail.database.impl;

import com.jumbotail.database.api.DBLockAccessObject;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBLockAccessObjectImpl implements DBLockAccessObject {
    @Override
    public boolean isLocked(String id) {
        return false;
    }

    @Override
    public boolean fetchLock(String id) {
        return false;
    }
}
