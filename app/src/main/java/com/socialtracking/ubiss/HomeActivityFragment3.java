package com.socialtracking.ubiss;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeActivityFragment3 extends Fragment {

    private ListView mListView;



    public HomeActivityFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_home_activity_fragment3, container, false);


//        mListView = (ListView) rootview.findViewById(R.id.recipe_list_view);
//
//        final String[] logsList = new String[]{"shkurta", "elcin", "hanke", "heng", "shkurta", "elcin", "hanke", "heng", "shkurta", "elcin", "hanke", "heng"};
//
//
//        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.list_item_view, logsList);
//        mListView.setAdapter(adapter);



        return rootview;
    }

}
