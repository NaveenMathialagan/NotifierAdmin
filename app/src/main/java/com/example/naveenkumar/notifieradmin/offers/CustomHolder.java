package com.example.naveenkumar.notifieradmin.offers;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.naveenkumar.notifieradmin.R;

/**
 * Created by Naveen kumar on 03-03-2016.
 */
public class CustomHolder  extends RecyclerView.ViewHolder {


    public TextView name;
    public TextView rate;
    CardView cardView;


    public CustomHolder(View view) {

        super(view);
        this.name = (TextView) view.findViewById(R.id.tvname);
        this.rate = (TextView) view.findViewById(R.id.tvrate);
        this.cardView=(CardView) view.findViewById(R.id.cardofr);
    }
}

