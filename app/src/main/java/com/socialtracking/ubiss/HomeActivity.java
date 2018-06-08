package com.socialtracking.ubiss;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.Keyboard;
import com.aware.providers.Keyboard_Provider;
import com.aware.ui.PermissionsHandler;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static String lastUsed = null;
    private BroadcastReceiver receiver;

    private final String FACEBOOK_PACKAGE = "com.facebook.katana";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new HomeActivityFragment(), "Mood");
        adapter.addFragment(new HomeActivityFragment2(), "Usage Purpose");
        adapter.addFragment(new HomeActivityFragment3(), "Summary");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Intent homeIntent = new Intent(HomeActivity.this, HomeActivity.class);
                //finish();
                //Return to home screen for now. We may change this later.
                moveTaskToBack(true);
                //tartActivity(homeIntent);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ESM.ACTION_AWARE_ESM_QUEUE_COMPLETE);
        filter.addAction(ESM.ACTION_AWARE_ESM_ANSWERED);
        filter.addAction(ESM.ACTION_AWARE_ESM_DISMISSED);
        this.registerReceiver(receiver, filter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<String> REQUIRED_PERMISSIONS = new ArrayList<>();
        REQUIRED_PERMISSIONS.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        retrieveKeyboardData();
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
            Aware.setSetting(this, Aware_Preferences.STATUS_KEYBOARD, true);

            Aware.startKeyboard(this);

            Applications.isAccessibilityServiceActive(getApplicationContext());


            Applications.setSensorObserver(new Applications.AWARESensorObserver() {
                @Override
                public void onForeground(ContentValues contentValues) {
                    Log.d("mood_foreground", contentValues.toString());
                    if(lastUsed != null) {
                        if (lastUsed.equals(FACEBOOK_PACKAGE) && !contentValues.get("package_name").toString().equals(FACEBOOK_PACKAGE)) {

                            unregisterReceiver(keyboardListener);

                            createESM();
                        }
                    }
                    if (!contentValues.getAsString("package_name").equals("com.sohu.inputmethod.sogou.xiaomi")) {
                        lastUsed = contentValues.get("package_name").toString();
                        if (lastUsed.equals("com.facebook.katana")) {
                            IntentFilter keyboardFilter = new IntentFilter(Keyboard.ACTION_AWARE_KEYBOARD);
                            registerReceiver(keyboardListener, keyboardFilter);
                        }
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
        else {
            Intent permissions = new Intent(this, PermissionsHandler.class);
            permissions.putExtra(PermissionsHandler.EXTRA_REQUIRED_PERMISSIONS, REQUIRED_PERMISSIONS);
            permissions.putExtra(PermissionsHandler.EXTRA_REDIRECT_ACTIVITY, getPackageName() + "/" + getClass().getName());
            permissions.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(permissions);

            finish();
        }
    }

    private static KeyboardListener keyboardListener = new KeyboardListener();
    public static class KeyboardListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Keyboard.ACTION_AWARE_KEYBOARD)) {
                //using keyboard in facebook
                Log.d("aware", "using keyboard inside facebook");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    // Adapter for the viewpager using FragmentPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private void createESM() {
        try {
            ESMFactory factory = new ESMFactory();
            ESM_PAM pam = new ESM_PAM();
            pam.setTitle("Mood Assessment");
            pam.setInstructions("Pick the closest to how you feel right now.");
            factory.addESM(pam);
            ESM.queueESM(getApplicationContext(), factory.build());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void retrieveKeyboardData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);


        Cursor cursor = getApplicationContext().getContentResolver().query(
                Keyboard_Provider.Keyboard_Data.CONTENT_URI, null,
                Keyboard_Provider.Keyboard_Data.PACKAGE_NAME +"="+"'com.facebook.katana'",null,
                Keyboard_Provider.Keyboard_Data.TIMESTAMP + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {

                Long answer = cursor.getLong(cursor.getColumnIndex(Keyboard_Provider.Keyboard_Data.TIMESTAMP));
                Log.d("mood_keyboard",Long.toString(answer));

            } while (cursor.moveToNext());
            cursor.close();
        }
    }

}
