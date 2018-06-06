package com.socialtracking.ubiss;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.aware.Aware;

public class MainService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        android.os.Debug.waitForDebugger();
        Intent aware = new Intent(this, Aware.class);
        startService(aware);
        }
}
