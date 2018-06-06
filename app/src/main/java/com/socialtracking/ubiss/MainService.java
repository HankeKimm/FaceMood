package com.socialtracking.ubiss;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.aware.Applications;
import com.aware.Aware;

public class MainService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Intent aware = new Intent(this, Aware.class);
        startService(aware);

        Applications.isAccessibilityServiceActive(this);

        Applications.setSensorObserver(new Applications.AWARESensorObserver() {
            @Override
            public void onForeground(ContentValues contentValues) {
                System.out.println(contentValues.describeContents());
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
