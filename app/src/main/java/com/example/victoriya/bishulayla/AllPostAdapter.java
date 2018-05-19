package com.example.victoriya.bishulayla;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Victoriya on 3/10/2018.
 */

public class AllPostAdapter extends ArrayAdapter<Post> {
    Context context;
    List<Post> objects;

    public AllPostAdapter(Context context, int resource, int textViewResourceId, List<Post> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context=context;
        this.objects=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.custom_post, parent, false);

        TextView tvNamePhone = (TextView)view.findViewById(R.id.tvNamePhone);
        ImageView iv=(ImageView) view.findViewById(R.id.iv);
        Post temp = objects.get(position);
        if (temp.bought.equals("true")) {
            iv.setImageResource(R.drawable.yes);
        }
    else {
            iv.setImageResource(R.drawable.no);
        }
        tvNamePhone.setText(temp.name+" "+temp.phone);

        return view;
    }
}


