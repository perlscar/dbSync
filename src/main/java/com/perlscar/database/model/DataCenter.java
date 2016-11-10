package com.perlscar.database.model;

import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.api.DBLockAccessObject;
import lombok.Data;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class DataCenter {

    private final DBAccessObject dbAccessObject;
    private final DBLockAccessObject dbLockAccessObject;
}
