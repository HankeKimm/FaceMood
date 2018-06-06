package com.socialtracking.ubiss;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;

public class MainService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {


        Applications.setSensorObserver(new Applications.AWARESensorObserver() {
            @Override
            public void onForeground(ContentValues contentValues) {
                Log.d("mood", contentValues.toString());
            }

            @Override
            public void onNotification(ContentValues contentValues) {

            }

            @Override
            public void onCrash(ContentValues contentValues) {

            }

            @Override
            public void onKeyboard(ContentValues contentValues) {

            }

            @Override
            public void onBackground(ContentValues contentValues) {

            }

            @Override
            public void onTouch(ContentValues contentValues) {

            }
        });
        }
}
