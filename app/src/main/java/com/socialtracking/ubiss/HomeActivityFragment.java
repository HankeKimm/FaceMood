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
}
