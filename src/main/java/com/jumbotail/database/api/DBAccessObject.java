package com.jumbotail.database.api;

import com.jumbotail.contract.EntryCreationResponse;
import com.jumbotail.contract.EntryDeletionResponse;
import com.jumbotail.contract.EntryUpdationResponse;
import com.jumbotail.exception.ObjectNotAccessibleException;

/**
 * Created by achit.ojha on 10/11/16.
 */
public interface DBAccessObject {
    /**
     * Get Object for a given Id
     * @param id : id for the object to be retrieved.
     * @return Object to be retrieved
     * @throws ObjectNotAccessibleException
     */
    public Object getById(String id) throws ObjectNotAccessibleException;

    /**
     * Create a new entry
     * @param id : id for the object to be retrieved.
     * @param object : Object to stored against the id.
     * @return EntryCreationResponse : describes the status of create request
     * @throws ObjectNotAccessibleException
     */
    public EntryCreationResponse createEntry(String id, String object) throws ObjectNotAccessibleException;

    /**
     * Update an existing entry
     * @param id : id for the object to be retrieved.
     * @param object : Object to updated against the id.
     * @return EntryUpdationResponse : describes the status of update request
     * @throws ObjectNotAccessibleException
     */
    public EntryUpdationResponse updateEntry(String id, String object) throws ObjectNotAccessibleException;

    /**
     * Delete an existing entry
     * @param id : id for the object to be retrieved.
     * @return EntryDeletionResponse : describes the status of delete request
     * @throws ObjectNotAccessibleException
     */
    public EntryDeletionResponse deleteEntry(String id, String object) throws ObjectNotAccessibleException;
}
