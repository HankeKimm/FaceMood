package com.socialtracking.ubiss;

import android.content.Context;
import android.database.Cursor;

import com.aware.providers.Applications_Provider;
import com.aware.providers.ESM_Provider;

import java.util.Calendar;
import java.util.HashMap;

public class DataManager {

    private Context context;

    public DataManager(Context context) {
        this.context = context;
    }

    public void retrieveESMSData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Cursor cursor = context.getContentResolver().query(
                ESM_Provider.ESM_Data.CONTENT_URI, null,
                ESM_Provider.ESM_Data.TIMESTAMP + ">=" +today.getTimeInMillis(), null,
                ESM_Provider.ESM_Data.TIMESTAMP + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String answer = cursor.getString(cursor.getColumnIndex(ESM_Provider.ESM_Data.ANSWER));

            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    public void retrieveFacebookData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Cursor cursor = context.getContentResolver().query(
                Applications_Provider.Applications_Foreground.CONTENT_URI, null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + ">=" +today.getTimeInMillis(), null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + " ASC");

        HashMap<Double, Double> facebook_usage = new HashMap<>();

        if (cursor != null && cursor.moveToFirst()) {

            double elapsed = 0;
            double timestamp_start = 0;

            do {
                if(cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = 0;
                    timestamp_start = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP));
                } else if (timestamp_start > 0 &&
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP))-timestamp_start;
                    facebook_usage.put(timestamp_start, elapsed);
                    timestamp_start = 0;
                }
            } while (cursor.moveToNext());
        }
    }
}
