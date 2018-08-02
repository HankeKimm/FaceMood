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

    private final String FACEBOOK_PACKAGE = "com.facebook.katana";
    private final String TWITTER_PACKAGE = "com.twitter.android";
    private final String INSTAGRAM_PACKAGE = "com.instagram.android";
    private final String MESSENGER_PACKAGE = "com.facebook.orca";
    private final String SNAPCHAT_PACKAGE = "com.snapchat.android";
    private final String DUO_PACKAGE = "com.google.android.apps.tachyon";
    private final String WHATSAPP_PACKAGE = "com.whatsapp";
    private final String VIBER_PACKAGE = "com.viber.voip";


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

                if (contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(FACEBOOK_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(TWITTER_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(INSTAGRAM_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(MESSENGER_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(SNAPCHAT_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(DUO_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(WHATSAPP_PACKAGE) ||
                        contentValues.getAsString(Applications_Provider.Applications_Foreground.PACKAGE_NAME).equals(VIBER_PACKAGE)) {
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
