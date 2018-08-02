package com.socialtracking.ubiss.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.socialtracking.ubiss.R;
import com.socialtracking.ubiss.models.FacebookDataItem;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacebookArrayAdapter extends ArrayAdapter<FacebookDataItem> {

    private Context context;
    private List<FacebookDataItem> data;
    private List<String> moodData;

    public FacebookArrayAdapter(@NonNull Context context, @NonNull List<FacebookDataItem> objects, @NonNull List<String> moodAnswers) {
        super(context, 0, objects);
        this.context = context;
        this.data = objects;
        this.moodData = moodAnswers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        FacebookDataItem item = data.get(position);
        String mood = moodData.get(position);


        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        ImageView image =  (ImageView) row.findViewById(R.id.moodImage);

//        String [] moods = new String[] {"tired", "gloomy", "sad","miserable","frustrated","angry", "afraid", "tense", "happy", "excited", "delighted", "glad", "calm", "satisfied", "serene", "sleepy"};

        ArrayList<String> positiveEmotions = new ArrayList<String>();
        positiveEmotions.add("excited");
        positiveEmotions.add("delighted");
        positiveEmotions.add("happy");
        positiveEmotions.add("glad");
        positiveEmotions.add("calm");
        positiveEmotions.add("satisfied");
        positiveEmotions.add("serene");
        positiveEmotions.add("sleepy");


        if(positiveEmotions.contains(mood)){
            image.setImageResource(R.drawable.positive);
        }else{
            image.setImageResource(R.drawable.negative);
        }

//        TextView textViewId = (TextView) row.findViewById(R.id.session_id);
//        textViewId.setText(String.format("Session id: %s", item.getSessionId()));


        /*
        TO DO
        CHANGE THIS TO RETRIEVE ONLY SOCIAL MEDIA APPS WE CONSIDER
         */
        TextView textViewSocialMediaApp = (TextView) row.findViewById(R.id.social_media_app);
        textViewSocialMediaApp.setText(String.format("%s", item.getSocialMediaApplication()));


        TextView textViewStart = (TextView) row.findViewById(R.id.session_start);
        Date currentDate = new Date(item.getSessionStart());
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        textViewStart.setText(String.format("Time: %s", df.format(currentDate)));

        TextView textViewLength = (TextView) row.findViewById(R.id.session_length);

        DecimalFormat dformat = new DecimalFormat("#.###");
        dformat.setRoundingMode(RoundingMode.CEILING);

        textViewLength.setText(String.format("Length: %s min", dformat.format(item.getSessionLength())));

        return row;
    }
}
