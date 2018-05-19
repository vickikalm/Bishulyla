package com.example.victoriya.bishulayla;

/**
 * Created by Victoriya on 3/29/2018.
 */
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
 * Created by ilana on 25/03/2018.
 */

public class ProdAdapter extends ArrayAdapter {
    Context context;
    List<Prod> noteList;

    public ProdAdapter(Context context, int resource, int textViewResourceId, List<Prod> objects) {
        super(context, resource, textViewResourceId, objects);

        this.context = context;
        this.noteList = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.one_row, parent, false);
        TextView tvProduct=(TextView)view.findViewById(R.id.tvProduct);
        TextView tvAmount=(TextView)view.findViewById(R.id.tvAmount);
        Prod temp = noteList.get(position);

        tvProduct.setText(temp.getProd());
        tvAmount.setText(temp.getAmount());



        return view;

    }


}

