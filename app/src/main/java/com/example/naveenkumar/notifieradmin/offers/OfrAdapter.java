package com.example.naveenkumar.notifieradmin.offers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.naveenkumar.notifieradmin.R;

import java.util.List;

/**
 * Created by Naveen kumar on 03-03-2016.
 */
public class OfrAdapter  extends RecyclerView.Adapter<CustomHolder>{
    private List<ofrinst> ofrinsts;
    //private Context mContext;


    public OfrAdapter(List<ofrinst> feedItemList) {
        this.ofrinsts = feedItemList;
       // this.mContext = context;
        Log.d("Noti","Adapdter constructor");
    }
    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ofr, parent, false);
        CustomHolder mh = new CustomHolder(v);

        Log.d("Noti","Adapdter oncreateView holder");

        return mh;
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) {
        final ofrinst ofrinst = ofrinsts.get(position);

           holder.name.setText(ofrinst.getOfrname());
            Log.d("Noti","On Bind name\t"+ofrinst.getOfrname());



            holder.rate.setText("Rs."+ofrinst.getOfrrate());
        Log.d("Noti","On Bind rate\t"+ofrinst.getOfrrate());

    }

    @Override
    public int getItemCount() {
        return ofrinsts.size();
    }
}
