package com.perlscar.exception;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class EntryNotPresentException extends DbSyncServiceException {
    public EntryNotPresentException(String message) {
        super(message);
    }

    public EntryNotPresentException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntryNotPresentException(Throwable cause) {
        super(cause);
    }

    public static EntryNotPresentException wrap(Throwable e) {
        return (e instanceof EntryNotPresentException) ?(EntryNotPresentException) e : new EntryNotPresentException(e);
    }

}
