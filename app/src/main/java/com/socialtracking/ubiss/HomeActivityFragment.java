package com.socialtracking.ubiss;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.socialtracking.ubiss.models.FacebookDataItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        LineChart facebookUsagechart = (LineChart) rootview.findViewById(R.id.facebookUsageChart);


        DataManager dataManager = new DataManager(getContext());
        ArrayList<String> esmAnswers = dataManager.retrieveESMSData();


        /*
        Negative and Positive emotions
         */
        ArrayList<String> positiveEmotions = new ArrayList<String>();
        positiveEmotions.add("excited");
        positiveEmotions.add("delighted");
        positiveEmotions.add("happy");
        positiveEmotions.add("glad");
        positiveEmotions.add("calm");
        positiveEmotions.add("satisfied");
        positiveEmotions.add("serene");
        positiveEmotions.add("sleepy");


//        ArrayList<String> negativeEmotions = new ArrayList<String>();
//        negativeEmotions.add("afraid");
//        negativeEmotions.add("tense");
//        negativeEmotions.add("angry");
//        negativeEmotions.add("frustrated");
//        negativeEmotions.add("miserable");
//        negativeEmotions.add("sad");
//        negativeEmotions.add("tired");
//        negativeEmotions.add("gloomy")


        /*
        Create and design the mood entries dataset
         */

        List<Entry> moodEntries = new ArrayList<>();

        int counter = 0;
        for (String s: esmAnswers){
            //If emotion in positive emotions set 0
            if(positiveEmotions.contains(s))
                moodEntries.add(new Entry(counter, 0));
            else
                moodEntries.add(new Entry(counter, 1));

            counter += 1;

        }

        LineDataSet dataset = new LineDataSet(moodEntries, "Mood (positive, negative)");
        dataset.setDrawCircles(true);
        dataset.setDrawFilled(true);
        dataset.disableDashedLine();
        dataset.setCircleRadius(5);
//        dataset.setCircleColor(R.drawable.happy);
        dataset.setCircleHoleRadius(5);
//        dataset.setCircleColorHole(R.color.colorPrimaryDark);


        LineData theDataset = new LineData(dataset);
        chart.setData(theDataset);


        /*
        Create and design the facebook usage entries dataset
         */

        List<Entry> facebookData = new ArrayList<>();

        List<FacebookDataItem> facebookUsageData = dataManager.retrieveFacebookData();


        float counter2 = 0;
        for(FacebookDataItem dataItem : facebookUsageData) {
            //Log.d("HomeActivity", "" + entry.getKey());

            double timestamp = dataItem.getSessionStart();
            double value = dataItem.getSessionLength();

            facebookData.add(new Entry(counter2,  (float) value, R.drawable.happy));
            counter2 += 1;
        }


        LineDataSet dataset2 = new LineDataSet(facebookData, "Facebook usage (mins)");
        dataset.setDrawCircles(true);
        dataset.setDrawFilled(true);
        dataset.disableDashedLine();
        dataset.setCircleRadius(5);
//        dataset.setCircleColor(R.drawable.happy);
        dataset.setCircleHoleRadius(5);
//        dataset.setCircleColorHole(R.color.colorPrimaryDark);


        LineData theDataset2 = new LineData(dataset2);
        facebookUsagechart.setData(theDataset2);

        return rootview;

    }

    private String convertUnixTimestampToDateString(long timestamp) {
        Date currentDate = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return df.format(currentDate);
    }
}
