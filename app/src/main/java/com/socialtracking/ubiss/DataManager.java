package com.socialtracking.ubiss;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.aware.ESM;
import com.aware.providers.Applications_Provider;
import com.aware.providers.ESM_Provider;
import com.aware.providers.Keyboard_Provider;
import com.socialtracking.ubiss.models.FacebookDataItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    private Context context;

    public DataManager(Context context) {
        this.context = context;
    }

    public ArrayList<String> retrieveESMSData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        ArrayList<String> ESM_answers = new ArrayList<String>();

        Cursor cursor = context.getContentResolver().query(
                ESM_Provider.ESM_Data.CONTENT_URI, null,
                ESM_Provider.ESM_Data.TIMESTAMP + ">=" + today.getTimeInMillis(), null,
                ESM_Provider.ESM_Data.TIMESTAMP + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String answer = cursor.getString(cursor.getColumnIndex(ESM_Provider.ESM_Data.ANSWER));
                ESM_answers.add(answer);
//                Log.d("Data Manager", answer);

            } while (cursor.moveToNext());
            cursor.close();
        }

        return ESM_answers;
    }

    public boolean retrieveKeyboardData(long sessionStart, double sessionLength) {

        long sessionEnd = sessionStart + (long)sessionLength;
        Log.d("mood_sessionStart",Long.toString(sessionStart));
        Log.d("mood_SessionEnd",Long.toString(sessionEnd));

        Cursor cursor = context.getContentResolver().query(
                Keyboard_Provider.Keyboard_Data.CONTENT_URI, null,
                Keyboard_Provider.Keyboard_Data.TIMESTAMP + ">=" + sessionStart + " AND " +
                        Keyboard_Provider.Keyboard_Data.TIMESTAMP + "<=" +sessionEnd, null,
                Keyboard_Provider.Keyboard_Data.TIMESTAMP + " ASC");
        if (cursor != null && cursor.moveToFirst()) {

            Log.d("debug", DatabaseUtils.dumpCursorToString(cursor));

            do {

                String answer = cursor.getString(cursor.getColumnIndex(Keyboard_Provider.Keyboard_Data.PACKAGE_NAME));
                Log.d("mood_PackageName",answer);
                if (answer.equals("com.facebook.katana"))
                {
                    cursor.close();
                    return true;
                }
                else
                    continue;

            } while (cursor.moveToNext());
            cursor.close();
            return false;
        } else{
            return false;
        }

    }

    public List<FacebookDataItem> retrieveFacebookData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Cursor cursor = context.getContentResolver().query(
                Applications_Provider.Applications_Foreground.CONTENT_URI, null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + ">=" +today.getTimeInMillis(), null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + " ASC");

        List<FacebookDataItem> facebookUsageList = new ArrayList<>();

        if (cursor != null && cursor.moveToFirst()) {

            double elapsed;
            long timestamp_start = 0;
            String sessionId = null;

            do {
                if(cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = 0;
                    timestamp_start = cursor.getLong(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP));
                    sessionId = cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground._ID));
                } else if (timestamp_start > 0
                        && !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP)) - timestamp_start;
                    facebookUsageList.add(new FacebookDataItem(sessionId, timestamp_start, elapsed));
                    timestamp_start = 0;
                }
            } while (cursor.moveToNext());
        }
        return facebookUsageList;
    }
    
}
