package com.socialtracking.ubiss;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ui.PermissionsHandler;

import android.Manifest;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!Aware.IS_CORE_RUNNING) {
            Intent aware = new Intent(getApplicationContext(), Aware.class);
            startService(aware);

            Applications.isAccessibilityServiceActive(getApplicationContext());

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
        /*Intent aware = new Intent(this, Aware.class);
        startService(aware);

        Aware.setSetting(this, Aware_Preferences.DEBUG_FLAG, true);
        Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);

        Applications.isAccessibilityServiceActive(this);*/

        ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        boolean permissions_ok = true;
        for (String p : REQUIRED_PERMISSIONS) { //loop to check all the required permissions.
            if (PermissionChecker.checkSelfPermission(this, p) != PermissionChecker.PERMISSION_GRANTED) {
                permissions_ok = false;
                break;
            }
        }

        if (permissions_ok) {
            Aware.setSetting(this, Aware_Preferences.DEBUG_FLAG, true);
            Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);
        }
        else {
            Intent permissions = new Intent(this, PermissionsHandler.class);
            permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
            permissions.putExtra(PermissionsHandler.EXTRA_REDIRECT_ACTIVITY, getPackageName() + "/" + getClass().getName());
            permissions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(permissions);

            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
