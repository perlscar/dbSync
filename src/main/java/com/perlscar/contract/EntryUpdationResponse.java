package com.perlscar.contract;

import lombok.Data;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class EntryUpdationResponse {
    private EntryUpdationStatus status;
    private String message;

    public EntryUpdationResponse(EntryUpdationStatus status) {
        this.status = status;
    }

}
