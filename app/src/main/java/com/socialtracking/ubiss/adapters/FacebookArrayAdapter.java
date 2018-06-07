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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

        FacebookDataItem item = data.get(position);

        if (row == null) {
            row = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

            TextView textViewId = (TextView) row.findViewById(R.id.session_id);
            textViewId.setText(String.format("Session id: %s", item.getSessionId()));

            TextView textViewStart = (TextView) row.findViewById(R.id.session_start);
            Date currentDate = new Date(item.getSessionStart());
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            textViewStart.setText(String.format("Session start: %s", df.format(currentDate)));

            TextView textViewLength = (TextView) row.findViewById(R.id.session_length);
            textViewLength.setText(String.format("Session length: %s ms", item.getSessionLength()));
        return row;
    }
}
