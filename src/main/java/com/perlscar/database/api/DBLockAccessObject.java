package com.perlscar.database.api;

import com.perlscar.exception.ObjectNotAccessibleException;

/**
 * Created by achit.ojha on 10/11/16.
 */
public interface DBLockAccessObject {

    /**
     * Checks in the lock DB for an entry corresponding to id
     * @param id : id against which the lock needs to be verified
     * @return true if object is locked, false otherwise
     */
    public boolean isLocked(String id) throws ObjectNotAccessibleException;

    /**
     * Acquires a lock against a given id
     * @param id : id against which the lock needs to be acquired
     * @return true if lock is acquired, false otherwise
     */
    public boolean fetchLock(String id) throws ObjectNotAccessibleException;

    /**
     * Release an acquired lock against a given id
     * @param id : id against which the lock needs to be released
     * @return true if lock isn't acquired or successfully released, false otherwise
     */
    public boolean releaseLock(String id) throws ObjectNotAccessibleException;
}
