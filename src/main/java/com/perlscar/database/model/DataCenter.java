package com.perlscar.database.model;

import com.perlscar.database.api.DBAccessObject;
import com.perlscar.database.api.DBLockAccessObject;
import lombok.Data;
import lombok.NonNull;

/**
 * Created by achit.ojha on 10/11/16.
 */
@Data
public class DataCenter {

    @NonNull private final DBAccessObject dbAccessObject;
    @NonNull private final DBLockAccessObject dbLockAccessObject;

}
