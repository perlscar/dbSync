package com.jumbotail.database.api;

/**
 * Created by achit.ojha on 10/11/16.
 */
public interface DBLockAccessObject {

    public boolean isLocked(String id);

    public boolean fetchLock(String id);
}
