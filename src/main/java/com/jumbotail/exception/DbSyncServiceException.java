package com.jumbotail.exception;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class DbSyncServiceException extends Exception {
    public DbSyncServiceException(String message) {
        super(message);
    }

    public DbSyncServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbSyncServiceException(Throwable cause) {
        super(cause);
    }

    public static DbSyncServiceException wrap(Throwable e) {
        return (e instanceof DbSyncServiceException) ?(DbSyncServiceException) e : new DbSyncServiceException(e);
    }

}
