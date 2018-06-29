package com.socialtracking.ubiss.LocalDataStorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.socialtracking.ubiss.User.User;
import com.socialtracking.ubiss.User.UsersContract;
import com.socialtracking.ubiss.User.UsersContract.UserEntry;


import java.util.List;



/**
 * Created by shkurtagashi on 18.12.16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = DatabaseHelper.class.getSimpleName();


    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /** Name of the database file */
    private static final String DATABASE_NAME = "moodbook.db";

    // Table Create Statements
    // String that contains the SQL statement to create the Users table
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserEntry.TABLE_NAME_USERS + " ("
            + UserEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserEntry.ANDROID_ID + " TEXT, "
            + UserEntry.USERNAME + " TEXT, "
            + UserEntry.EMAIL + " TEXT, "
            + UserEntry.COLUMN_GENDER + " TEXT, "
            + UserEntry.COLUMN_AGE + " TEXT);";


    /**
     * Constructs a new instance of {@link DatabaseHelper}.
     *
     * @param context of the app
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db){
        // Execute the SQL statements
        db.execSQL(SQL_CREATE_USERS_TABLE);



//        insertRecords(db, UploaderUtilityTable.TABLE_UPLOADER_UTILITY, UploaderUtilityTable.getRecords());

    }


    private void insertRecords(SQLiteDatabase db, String tableName, List<ContentValues> records) {
        for(ContentValues record: records) {
            db.insert(tableName, null, record);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL(SQL_DELETE_ENTRIES);
//        onCreate(db);
    }


    private static DatabaseHelper dbhelper;

    //getInstance method used where we need instance of RegDatabaseHandler to insert, update or delete data
    public static synchronized DatabaseHelper getInstance(Context context){
        if(dbhelper == null){
            dbhelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbhelper;
    }

    public User getUserInformation(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        User user = new User();

        Cursor cursor = db.query(UserEntry.TABLE_NAME_USERS, new String[]{UserEntry.USERNAME,
                        UserEntry.COLUMN_GENDER, UserEntry.COLUMN_AGE},
                UserEntry.USERNAME +"=?", new String[]{String.valueOf(username)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            user = new User(cursor.getString(cursor.getColumnIndex(UserEntry.USERNAME)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGE))
            );
            cursor.close();
        }
        return user;
    }

    public User getUserInfo(String androidID){
        SQLiteDatabase db = this.getReadableDatabase();

        User user = new User();

        Cursor cursor = db.query(UserEntry.TABLE_NAME_USERS, new String[]{UserEntry.USERNAME, UserEntry.EMAIL,
                        UserEntry.COLUMN_GENDER, UserEntry.COLUMN_AGE},
                UserEntry.ANDROID_ID +"=?", new String[]{String.valueOf(androidID)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            user = new User(cursor.getString(cursor.getColumnIndex(UserEntry.USERNAME)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.EMAIL)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_GENDER)),
                    cursor.getString(cursor.getColumnIndex(UserEntry.COLUMN_AGE))
            );
            cursor.close();
        }
        return user;
    }


    /*
        Add a user into Users table
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        Log.v("DBBBBBBB", "WE HEREEE");


        db.beginTransaction();

        try {
            ContentValues values = new ContentValues();

            values.put(UserEntry.ANDROID_ID, user.getAndroidId());
            values.put(UserEntry.USERNAME, user.getUsername());
            values.put(UserEntry.EMAIL, user.getEmail());
            values.put(UserEntry.COLUMN_GENDER, user.getGender());
            values.put(UserEntry.COLUMN_AGE, user.getAge());

            db.insertOrThrow(UserEntry.TABLE_NAME_USERS, null, values);
            db.setTransactionSuccessful();
            System.out.println("USER DATA INSERTED: "+ values);
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error while trying to add USER to database");
        } finally {
            db.endTransaction();
            db.close();
        }
    }




}
