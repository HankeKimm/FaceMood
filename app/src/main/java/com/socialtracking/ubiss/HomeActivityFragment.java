package com.socialtracking.ubiss;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.socialtracking.ubiss.models.FacebookDataItem;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        /**
         * Retrieving DataManager.
         */
        DataManager dataManager = new DataManager(getContext());

        //Creating Facebook Usage Chart.
        createFacebookUsageChart(dataManager, rootView);


//        //Creating Mood Chart.
//        createMoodChart(dataManager, rootView);

        return rootView;

    }

    private String convertUnixTimestampToDateString(long timestamp) {
        Date currentDate = new Date(timestamp);
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        return df.format(currentDate);
    }

    private IAxisValueFormatter createAxisFormatter(final FacebookDataItem[] items) {
        IAxisValueFormatter formatter = new IAxisValueFormatter() {

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return convertUnixTimestampToDateString(items[(int) value].getSessionStart());
            }
        };
        return formatter;
    }

    private void createFacebookUsageChart(DataManager dataManager, View rootView){

        List<Entry> facebookData = new ArrayList<>();

        List<FacebookDataItem> facebookUsageData = dataManager.retrieveFacebookData();
        List<String> esmAnswers = dataManager.retrieveESMSData();


        float xPointCounter = 0;
        for(FacebookDataItem dataItem : facebookUsageData) {
            double value = dataItem.getSessionLength();
            facebookData.add(new Entry(xPointCounter,  (float) value));
            xPointCounter += 1;
        }

        LineChart facebookUsagechart = (LineChart) rootView.findViewById(R.id.facebookUsageChart);

        if(facebookData.isEmpty()) {
            facebookUsagechart.setNoDataText("Chart is Empty. Start using Facebook to see some data!");
        } else {
            LineDataSet faceBookLineDataset = new LineDataSet(facebookData, "Social media usage (mins)");
            faceBookLineDataset.setDrawCircles(true);
            faceBookLineDataset.setDrawFilled(true);
            faceBookLineDataset.disableDashedLine();
            faceBookLineDataset.setCircleRadius(5);
            faceBookLineDataset.setCircleHoleRadius(5);
            faceBookLineDataset.setValueTextColor(0x7fffffff);

            ArrayList<String> positiveEmotions = new ArrayList<String>();
            positiveEmotions.add("excited");
            positiveEmotions.add("delighted");
            positiveEmotions.add("happy");
            positiveEmotions.add("glad");
            positiveEmotions.add("calm");
            positiveEmotions.add("satisfied");
            positiveEmotions.add("serene");
            positiveEmotions.add("sleepy");


             /*
            Setting the smiley for each point
             */

            if(!esmAnswers.isEmpty()){
                int counter2 = 0;
                for (String s: esmAnswers){
                    if(positiveEmotions.contains(s)){
                        faceBookLineDataset.getEntryForIndex(counter2).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.happy2));
                    }
                    else{
                        faceBookLineDataset.getEntryForIndex(counter2).setIcon(ContextCompat.getDrawable(getContext(), R.drawable.sad));
                    }

                    counter2 += 1;
                }
            }

            LineData facebookLineData = new LineData(faceBookLineDataset);
            facebookUsagechart.setData(facebookLineData);

            Legend legend = facebookUsagechart.getLegend();
            legend.setTextColor(0x7fffffff);

            final FacebookDataItem[] items = facebookUsageData.toArray(new FacebookDataItem[facebookUsageData.size()]);
            IAxisValueFormatter formatter = createAxisFormatter(items);

            XAxis xAxis = facebookUsagechart.getXAxis();
            xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
            xAxis.setAxisMinimum(0f);
            xAxis.setValueFormatter(formatter);
            xAxis.setTextColor(0x7fffffff);


            YAxis leftYAxis = facebookUsagechart.getAxis(YAxis.AxisDependency.LEFT);
            leftYAxis.setTextColor(0x7fffffff);

            YAxis rightYAxis = facebookUsagechart.getAxis(YAxis.AxisDependency.RIGHT);
            rightYAxis.setTextColor(0x7fffffff);
        }
    }

//    private void createMoodChart(DataManager dataManager, View rootView) {
//        LineChart moodChart = (LineChart) rootView.findViewById(R.id.moodChart);
//
//        ArrayList<String> esmAnswers = dataManager.retrieveESMSData();
//
//        /*
//        Negative and Positive emotions
//         */
//        ArrayList<String> positiveEmotions = new ArrayList<String>();
//        positiveEmotions.add("excited");
//        positiveEmotions.add("delighted");
//        positiveEmotions.add("happy");
//        positiveEmotions.add("glad");
//        positiveEmotions.add("calm");
//        positiveEmotions.add("satisfied");
//        positiveEmotions.add("serene");
//        positiveEmotions.add("sleepy");
//
//        /**
//         * Create and design the mood entries dataset
//         */
//        List<Entry> moodEntries = new ArrayList<>();
////        moodEntries.add(new Entry(0, 0));
//
////        int counter = 0;
////        for (String s: esmAnswers){
////            //If emotion in positive emotions set 0
////            if(positiveEmotions.contains(s))
////                moodEntries.add(new Entry(counter, 0));
////            else
////                moodEntries.add(new Entry(counter, 1));
////            counter += 1;
////        }
//
//        if(moodEntries.isEmpty()) {
//            moodChart.setNoDataText("Chart is Empty. Start using Facebook to see some data!");
//        } else {
//            LineDataSet moodDataSet = new LineDataSet(moodEntries, "Mood (positive, negative)");
//            moodDataSet.setDrawCircles(true);
//            moodDataSet.setDrawFilled(true);
//            moodDataSet.disableDashedLine();
//            moodDataSet.setCircleRadius(5);
//            moodDataSet.setCircleHoleRadius(5);
//            moodDataSet.setValueTextColor(0x7fffffff);
//
//
//            LineData moodLineData = new LineData(moodDataSet);
//            moodChart.setData(moodLineData);
//
//            Legend legend = moodChart.getLegend();
//            legend.setTextColor(0x7fffffff);
//
//            List<FacebookDataItem> facebookUsageData = dataManager.retrieveFacebookData();
//            final FacebookDataItem[] items = facebookUsageData.toArray(new FacebookDataItem[facebookUsageData.size()]);
//
//            IAxisValueFormatter formatter = createAxisFormatter(items);
//
//            XAxis moodChartXAxis = moodChart.getXAxis();
//            moodChartXAxis.setTextColor(0x7fffffff);
//
//            moodChartXAxis.setGranularity(1f); // minimum axis-step (interval) is 1
//            moodChartXAxis.setAxisMinimum(0f);
//            moodChartXAxis.setValueFormatter(formatter);
//
//            YAxis leftYAxis = moodChart.getAxis(YAxis.AxisDependency.LEFT);
//            leftYAxis.setTextColor(0x7fffffff);
//
//            YAxis rightYAxis = moodChart.getAxis(YAxis.AxisDependency.RIGHT);
//            rightYAxis.setTextColor(0x7fffffff);
//        }
//    }
}
