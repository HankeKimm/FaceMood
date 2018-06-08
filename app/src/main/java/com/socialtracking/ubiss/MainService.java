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
import com.aware.Keyboard;
import com.aware.providers.Applications_Provider;

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

                if (contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals("com.facebook.katana")) {
                    Aware.startKeyboard(getApplicationContext());

                }
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
