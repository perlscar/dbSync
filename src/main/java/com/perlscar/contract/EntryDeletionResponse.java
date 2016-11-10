package com.perlscar.contract;

import lombok.Data;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class EntryDeletionResponse {
    private EntryDeletionStatus status;
    private String message;

    public EntryDeletionResponse(EntryDeletionStatus status) {
        this.status = status;
    }
}
