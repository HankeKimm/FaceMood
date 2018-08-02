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

    private final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private final String TWITTER_PACKAGE = "com.twitter.android";
    private final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private final String MESSENGER_PACKAGE = "com.facebook.orca";
    private final String SNAPCHAT_PACKAGE = "com.snapchat.android";
    private final String DUO_PACKAGE = "com.google.android.apps.tachyon";
    private final String WHATSAPP_PACKAGE = "com.whatsapp";
    private final String VIBER_PACKAGE = "com.viber.voip";


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
                if (answer.equals(FACEBOOK_PACKAGE) || answer.equals(TWITTER_PACKAGE) || answer.equals(MESSENGER_PACKAGE) || answer.equals(INSTAGRAM_PACKAGE) ||
                        answer.equals(DUO_PACKAGE) || answer.equals(VIBER_PACKAGE) || answer.equals(WHATSAPP_PACKAGE) || answer.equals(SNAPCHAT_PACKAGE)) {
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
            String socialMediaApp = null;

            do {
                if(cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(FACEBOOK_PACKAGE) ||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(TWITTER_PACKAGE) ||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(INSTAGRAM_PACKAGE) ||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(DUO_PACKAGE) ||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(MESSENGER_PACKAGE)||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(WHATSAPP_PACKAGE) ||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(VIBER_PACKAGE)||
                        cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(SNAPCHAT_PACKAGE)) {
                    elapsed = 0;
                    timestamp_start = cursor.getLong(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP));
                    sessionId = cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground._ID));
                } else if (timestamp_start > 0
                        && (!cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(FACEBOOK_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(TWITTER_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(INSTAGRAM_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(DUO_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(MESSENGER_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(WHATSAPP_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(VIBER_PACKAGE) ||
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals(SNAPCHAT_PACKAGE))) {
                    elapsed = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP)) - timestamp_start;
                    socialMediaApp = cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME));
                    facebookUsageList.add(new FacebookDataItem(sessionId, socialMediaApp, timestamp_start, elapsed));
                    timestamp_start = 0;
                }
            } while (cursor.moveToNext());
        }
        return facebookUsageList;
    }
}
