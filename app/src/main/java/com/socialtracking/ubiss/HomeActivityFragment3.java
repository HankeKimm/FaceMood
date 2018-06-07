package com.socialtracking.ubiss;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


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
        View view = inflater.inflate(R.layout.fragment_home_activity_fragment3, container, false);

        mListView = (ListView) view.findViewById(R.id.facebook_log_list_view);
// 1
        ArrayList<String> testList = new ArrayList<>();
        testList.add("Hanke");
// 2
        /*String[] listItems = new String[recipeList.size()];
// 3
        for(int i = 0; i < recipeList.size(); i++){
            Recipe recipe = recipeList.get(i);
            listItems[i] = recipe.title;
        }*/
// 4
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, testList);

        mListView.setAdapter(adapter);

        return view;
    }

}
