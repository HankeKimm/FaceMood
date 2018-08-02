package com.socialtracking.ubiss.Reminders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
//import android.icu.util.Calendar;

import java.util.Calendar;
import java.util.Random;


/**
 * Created by shkurtagashi on 03.07.18.
 */

public class FinalScheduler {

    private Weekday session11 = new Weekday(12, 6, Calendar.TUESDAY, "PAM 1");
    private Weekday session12 = new Weekday(12, 7, Calendar.TUESDAY, "PAM 2");


    private Calendar createCalendar(int day, int hour, int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.DAY_OF_WEEK, day);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

      /* Creation of the reminder for providing baseline about MOOD */
    /********** Reminder **********/
    public void createReminder(Context context) {

        Weekday [] reminders = {session11, session12};

        Random rand = new Random();
        int code;

        for (Weekday w: reminders) {
            code = rand.nextInt(100000000);
            setAlarm(context, code, w.getDescription(), w);
            Log.v("Scheduler", "Session: "+ w.getDescription() + "code: "+code);

        }

    }

    public void setAlarm(Context context, int requestCode, String session, Weekday weekday){
        Intent myIntent = new Intent(context, AlarmNotificationReceiver.class);
        myIntent.putExtra("Session", session);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, requestCode, myIntent, PendingIntent.FLAG_ONE_SHOT);
        Calendar calendar1 = createCalendar(weekday.getDay2(),weekday.getHour(), weekday.getMinute());

        if (calendar1.getTimeInMillis() > System.currentTimeMillis()) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            Log.v("Final Scheduler", "Alarms all set 1");
        } else {
            calendar1.add(java.util.Calendar.DAY_OF_MONTH, 1);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), pendingIntent1);
            Log.v("Final Scheduler", "Alarms all set 2");
        }

    }






}

