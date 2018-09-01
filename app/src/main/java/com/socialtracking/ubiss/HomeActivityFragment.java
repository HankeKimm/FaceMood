package com.socialtracking.ubiss;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
}
