package com.socialtracking.ubiss;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
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

        int[] labels = new int[]{1, 0, 0, 1};

        LineDataSet dataset = new LineDataSet(facebookEntries, "Facebook usage (mins)");
        dataset.setDrawCircles(true);
        dataset.setDrawFilled(true);
        dataset.disableDashedLine();
        dataset.setCircleRadius(5);
//        dataset.setCircleColor(R.drawable.happy);
        dataset.setCircleHoleRadius(5);
        dataset.setCircleColorHole(R.color.colorPrimaryDark);



//        for (int mood: labels) {
//            if (mood == 1) {
//                dataset.setCircleColorHole(R.color.primary);
//                Log.d("HomeActivitye", "" + mood);
//            } else {
//                dataset.setCircleColorHole(R.color.accent);
//
//            }
//        }



        LineData theDataset = new LineData(dataset);
        chart.setData(theDataset);

        return rootview;

    }
}
