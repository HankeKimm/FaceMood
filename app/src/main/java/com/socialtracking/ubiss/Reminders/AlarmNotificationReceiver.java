package com.socialtracking.ubiss.Reminders;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


import com.aware.ui.esms.ESMFactory;
import com.aware.ui.esms.ESM_PAM;
import com.socialtracking.ubiss.MainActivity;
import com.socialtracking.ubiss.PAMActivity;
import com.socialtracking.ubiss.R;
import com.socialtracking.ubiss.SurveysFragment;

import org.json.JSONException;

import java.util.Random;

/**
 *
 */

public class AlarmNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("I am in receiver!");

        String session = intent.getExtras().get("Session").toString();


        if(session.equals("PAM 1") || session.equals("PAM 2") || session.equals("PAM 3") || session.equals("PAM 4")){
            setNotification(context, "Survey Time", "Please do not forget to tell us how you feel right now - " + session + "!", session, 1000099);
        }
    }



    public void setNotification(Context context, String title, String content, String session, int notificationID){
        Bundle bundle = new Bundle();

        Random rand = new Random();
        int code = rand.nextInt(100000000);
        System.out.println("code: "+code);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
//        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        builder.setAutoCancel(true);
        builder.setOngoing(true);
//        builder.setChannel("Reminders");

        Intent intent = new Intent(context, PAMActivity.class);
        bundle.putString("paper", session);
        intent.putExtras(bundle);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(PAMActivity.class);
        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logo);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, builder.build());

    }



//    public void setNotification2(Context context, String title, String content, int notificationID){
//        Random rand = new Random();
//        int code = rand.nextInt(1000000);
//        System.out.println("code: "+code);
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setContentTitle(title);
//        builder.setContentText(content);
////        builder.setDefaults(Notification.DEFAULT_SOUND);
//        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
//        builder.setAutoCancel(true);
//        builder.setOngoing(true);
////        builder.setChannel("Memorable");
//
//
//        Intent intent = new Intent(context, Questionnaire.class);
//
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(code, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        builder.setContentIntent(pendingIntent);
//        builder.setSmallIcon(R.drawable.logo3);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//        notificationManager.notify(notificationID, builder.build());
//
//    }
}
