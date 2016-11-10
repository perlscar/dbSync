package com.perlscar.exception;

/**
 * Created by achit.ojha on 10/11/16.
 */
public class ObjectNotAccessibleException extends DbSyncServiceException {
    public ObjectNotAccessibleException(String message) {
        super(message);
    }

    public ObjectNotAccessibleException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotAccessibleException(Throwable cause) {
        super(cause);
    }

    public static ObjectNotAccessibleException wrap(Throwable e) {
        return (e instanceof ObjectNotAccessibleException) ?(ObjectNotAccessibleException) e : new ObjectNotAccessibleException(e);
    }

}
