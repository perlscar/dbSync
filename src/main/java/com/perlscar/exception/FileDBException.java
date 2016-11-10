package com.perlscar.exception;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class FileDBException extends DbSyncServiceException {
    public FileDBException(String message) {
        super(message);
    }

    public FileDBException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileDBException(Throwable cause) {
        super(cause);
    }

    public static FileDBException wrap(Throwable e) {
        return (e instanceof FileDBException) ?(FileDBException) e : new FileDBException(e);
    }

}
