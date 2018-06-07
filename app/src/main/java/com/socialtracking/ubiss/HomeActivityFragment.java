package com.socialtracking.ubiss;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aware.Applications;
import com.aware.providers.Applications_Provider;
import com.aware.providers.ESM_Provider;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class HomeActivityFragment extends Fragment {

    public HomeActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview =  inflater.inflate(R.layout.fragment_home, container, false);

        LineChart chart = (LineChart) rootview.findViewById(R.id.moodChart);

        List<Entry> facebookEntries = new ArrayList<>();
        facebookEntries.add(new Entry(0f, 1));
        facebookEntries.add(new Entry(1f, 2));
        facebookEntries.add(new Entry(2f, 10));
        facebookEntries.add(new Entry(3f, 5));

//        ArrayList<String> labels = new ArrayList<>();
//        labels.add("Positive");
//        labels.add("Negative");
//        labels.add("Negative");
//        labels.add("Positive");

        LineDataSet dataset = new LineDataSet(facebookEntries, "Facebook usage (mins)");
        dataset.setDrawCircles(true);
        dataset.setDrawFilled(true);
        dataset.setCubicIntensity(5);

        ArrayList<Entry> moodEntries = new ArrayList<>();
        moodEntries.add(new Entry(0f, 0));
        moodEntries.add(new Entry(1f, 1));
        moodEntries.add(new Entry(2f, 1));
        moodEntries.add(new Entry(3f, 0));

        LineDataSet dataset2 = new LineDataSet(moodEntries, "Mood");
        dataset2.setDrawCircles(true);

        LineData theDataset = new LineData(dataset, dataset2);
        chart.setData(theDataset);

        return rootview;

    }

    private void retrieveESMS() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Cursor cursor = getContext().getContentResolver().query(
                ESM_Provider.ESM_Data.CONTENT_URI, null,
                ESM_Provider.ESM_Data.TIMESTAMP + ">=" +today.getTimeInMillis(), null,
                ESM_Provider.ESM_Data.TIMESTAMP + " ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {

                String answer = cursor.getString(cursor.getColumnIndex(ESM_Provider.ESM_Data.ANSWER));

            } while (cursor.moveToNext());
            cursor.close();
        }
    }

    private void retrieveData() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY,0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Cursor cursor = getContext().getContentResolver().query(
                Applications_Provider.Applications_Foreground.CONTENT_URI, null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + ">=" +today.getTimeInMillis(), null,
                Applications_Provider.Applications_Foreground.TIMESTAMP + " ASC");

        HashMap<Double, Double> facebook_usage = new HashMap<>();

        if (cursor != null && cursor.moveToFirst()) {

            double elapsed = 0;
            double timestamp_start = 0;

            do {
                if(cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = 0;
                    timestamp_start = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP));
                } else if (timestamp_start > 0 &&
                        !cursor.getString(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.PACKAGE_NAME)).equals("com.facebook.katana")) {
                    elapsed = cursor.getDouble(cursor.getColumnIndex(Applications_Provider.Applications_Foreground.TIMESTAMP))-timestamp_start;
                    facebook_usage.put(timestamp_start, elapsed);
                    timestamp_start = 0;
                }
            } while (cursor.moveToNext());
        }
    }
}
