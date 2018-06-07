package com.socialtracking.ubiss.adapters;

import android.app.Activity;
import android.content.Context;
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

import java.util.List;

public class FacebookArrayAdapter extends ArrayAdapter<FacebookDataItem> {

    private Context context;
    private List<FacebookDataItem> data;

    public FacebookArrayAdapter(@NonNull Context context, @NonNull List<FacebookDataItem> objects) {
        super(context, 0, objects);
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

            TextView textViewId = (TextView) row.findViewById(R.id.session_id);
            textViewId.setText(data.get(position).getSessionId());
            TextView textViewStart = (TextView) row.findViewById(R.id.session_start);
            textViewStart.setText(String.valueOf(data.get(position).getSessionStart()));
        return row;
    }
}
