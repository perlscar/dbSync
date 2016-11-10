package com.jumbotail.database.model;

import com.jumbotail.database.api.DBAccessObject;
import com.jumbotail.database.api.DBLockAccessObject;
import lombok.Data;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class DataCenter {

    private final DBAccessObject dbAccessObject;
    private final DBLockAccessObject dbLockAccessObject;
}
