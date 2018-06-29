package com.socialtracking.ubiss.User;

import android.provider.BaseColumns;

/**
 * Created by shkurtagashi on 28.06.18.
 */

public final class UsersContract {

    // Private constructor to prevent someone from accidentally instantiating the contract class,
    private UsersContract() {}

    /* Inner class that defines the table contents */
    public static final class UserEntry implements BaseColumns {

        /*
        * Users - Table and Columns declaration
         */
        public final static String TABLE_NAME_USERS = "usersTable";

        public final static String _ID = BaseColumns._ID;
        public final static String ANDROID_ID = "android_id";
        public final static String USERNAME = "username";
        public final static String EMAIL = "email";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_AGE = "age";


        /**
         * Possible values for the gender of the user
         */
        public static final String GENDER_MALE = "M";
        public static final String GENDER_FEMALE = "F";


        public static String[] getColumns(){
            String[] columns = {_ID, ANDROID_ID, USERNAME, EMAIL, COLUMN_GENDER, COLUMN_AGE};

            return columns;
        }



    }


}
