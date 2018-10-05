package com.example.tglover_feelsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

/**
 * used code from code path and edited from various resources on stack exchange.
 * https://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView
 */
public class CustomAdapter extends ArrayAdapter<Emotion> {
    private static final String TAG = "CustomAdapter";
    private Context mContext;
    private int mResource;




    public CustomAdapter(Context context,int resource, ArrayList<Emotion> stuff){
        super(context,resource, stuff);
        mResource = resource;
        mContext = context;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        String EmotionName = getItem(position).getEmotion();
        Date Datestr = getItem(position).getDate();
        String commentstr = getItem(position).getComment();

        Emotion emotion = new Emotion(EmotionName,Datestr,commentstr);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView emot = (TextView) convertView.findViewById(R.id.EmotionView);
        TextView date = (TextView) convertView.findViewById(R.id.DateView);
        TextView comment = (TextView) convertView.findViewById(R.id.CommentView);

        emot.setText(EmotionName);
        date.setText(Datestr.toString());
        comment.setText(commentstr);

        return convertView;

    }
}
