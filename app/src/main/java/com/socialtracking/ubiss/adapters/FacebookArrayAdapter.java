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

import java.text.DateFormat;
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
        positiveEmotions.add("afraid");
        positiveEmotions.add("tense");
        positiveEmotions.add("angry");
        positiveEmotions.add("frustrated");
        positiveEmotions.add("miserable");
        positiveEmotions.add("sad");
        positiveEmotions.add("tired");
        positiveEmotions.add("gloomy");

//        if(positiveEmotions.contains(mood))
//            image.setImageResource(R.drawable.happy);

        if(mood.equals("afraid")){
            image.setImageResource(R.drawable.afraid);
        }else if (mood.equals("tense")) {
            image.setImageResource(R.drawable.tense);
        }else{
            image.setImageResource(R.drawable.happy);
        }

//        TextView textViewId = (TextView) row.findViewById(R.id.session_id);
//        textViewId.setText(String.format("Session id: %s", item.getSessionId()));

        TextView textViewStart = (TextView) row.findViewById(R.id.session_start);
        Date currentDate = new Date(item.getSessionStart());
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        textViewStart.setText(String.format("Time accessed: %s", df.format(currentDate)));

        TextView textViewLength = (TextView) row.findViewById(R.id.session_length);
        textViewLength.setText(String.format("Usage lenght: %s minutes", item.getSessionLength()));


        return row;
    }
}
