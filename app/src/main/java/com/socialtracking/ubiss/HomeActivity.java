package com.socialtracking.ubiss;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.aware.Applications;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.ESM;
import com.aware.ui.PermissionsHandler;
import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    static String lastUsed = null;


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
        adapter.addFragment(new HomeActivityFragment2(), "Purpose");
        adapter.addFragment(new HomeActivityFragment3(), "Statistics");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();


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

            //Intent aware = new Intent(getApplicationContext(), Aware.class);
            //startService(aware);

            Aware.setSetting(this, Aware_Preferences.DEBUG_FLAG, true);
            Aware.setSetting(this, Aware_Preferences.STATUS_APPLICATIONS, true);

            /*Aware.startScreen(this);
            Screen.setSensorObserver(new Screen.AWARESensorObserver() {
                @Override
                public void onScreenOn() {
                    Log.d("mood", "ON");
                }

                @Override
                public void onScreenOff() {

                }

                @Override
                public void onScreenLocked() {

                }

                @Override
                public void onScreenUnlocked() {

                }
            });*/

            /*Intent applications = new Intent(this, Applications.class);
            startService(applications);*/

            Applications.isAccessibilityServiceActive(getApplicationContext());


            Applications.setSensorObserver(new Applications.AWARESensorObserver() {
                @Override
                public void onForeground(ContentValues contentValues) {
                    Log.d("mood", contentValues.toString());
                    if((lastUsed != null)) {
                        if (lastUsed.equals("com.facebook.katana") && !contentValues.get("package_name").toString().equals("com.facebook.katana")) {
                            try {
                                ESMFactory factory = new ESMFactory();
                                ESM_PAM pam = new ESM_PAM();
                                pam.setTitle("PAM");
                                pam.setInstructions("Pick the closest to how you feel right now.");
                                factory.addESM(pam);
                                ESM.queueESM(getApplicationContext(), factory.build());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    lastUsed = contentValues.get("package_name").toString();
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

}
