package com.example.naveenkumar.notifieradmin;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by VSRK on 12/22/2015.
 */
public class CustomViewHolder extends RecyclerView.ViewHolder {


    public ImageView imageView;
    //public TextView textView;
    public TextView pack;
    public TextView title;
    public TextView text;
    public TextView time;
    CardView cardView;

    public CustomViewHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        this.pack = (TextView) view.findViewById(R.id.pack);
        this.text = (TextView) view.findViewById(R.id.text);
        this.imageView=(ImageView) view.findViewById(R.id.appicon);
       this.cardView=(CardView) view.findViewById(R.id.card);
        this.time=(TextView)view.findViewById(R.id.time);




    }
}