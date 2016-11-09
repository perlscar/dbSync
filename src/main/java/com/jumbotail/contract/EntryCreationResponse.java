package com.jumbotail.contract;

import lombok.Data;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class EntryCreationResponse {
    private EntryCreationStatus status;
    private String message;

    public EntryCreationResponse(EntryCreationStatus status) {
        this.status = status;
    }

}
