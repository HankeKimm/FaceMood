package com.socialtracking.ubiss.LocalDataStorage;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;
import java.util.Map;

/**
 * Created by shkurta on 28/06/18.
 */

public interface LocalStorageController {
    Cursor rawQuery(String query, String[] args);
    void insertRecords(String tableName, List<Map<String, String>> records);
    long getDbSize();
    void delete(String tableName, String clause);
    void update(String tableName, ContentValues values, String clause);


}
