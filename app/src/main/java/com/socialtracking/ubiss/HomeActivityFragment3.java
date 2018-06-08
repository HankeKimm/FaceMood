package com.socialtracking.ubiss;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.socialtracking.ubiss.adapters.FacebookArrayAdapter;
import com.socialtracking.ubiss.models.FacebookDataItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeActivityFragment3 extends Fragment {

    private ListView mListView;
    TextView facebookUsage;


    public HomeActivityFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_activity_fragment3, container, false);
        mListView = (ListView) view.findViewById(R.id.facebook_log_list_view);

        facebookUsage = (TextView) view.findViewById(R.id.totalUsage);

//        DataManager dataManager = new DataManager(getActivity());
//        List<FacebookDataItem> facebookLogList = dataManager.retrieveFacebookData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        DataManager dataManager = new DataManager(getActivity());
        List<FacebookDataItem> facebookLogList = dataManager.retrieveFacebookData();

        DataManager dataManager2 = new DataManager(getContext());
        ArrayList<String> esmAnswers = dataManager.retrieveESMSData();

        ArrayAdapter adapter = new FacebookArrayAdapter(getActivity(), facebookLogList, esmAnswers);

        int minutes = 0;
        for(FacebookDataItem item: facebookLogList){
            minutes += item.getSessionLength();
        }

        facebookUsage.setText(String.format("Today you have used facebook for %s minutes. The average daily usage of facebook is 34 minutes per day.", minutes));


        mListView.setAdapter(adapter);
    }
}
