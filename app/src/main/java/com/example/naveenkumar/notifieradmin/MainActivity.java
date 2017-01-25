package com.example.naveenkumar.notifieradmin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.naveenkumar.notifieradmin.offers.viewofrs;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeContainer;
    RecyclerView mrecyclerView;
    AppNotifyAdapter adapter;
    List<Notify> contacts;
    Button btnofr,ofredt;
    EditText edofrname,edofrrate;
    String ofrname;
    String ofrrate;
    boolean ins;
   ImageView img;

    public static String URL="http://www.thecityshoppers.com/offer.php";
    public static String UPLOAD_URL="http://www.thecityshoppers.com/process1.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.img);

        btnofr= (Button) findViewById(R.id.btnofr);
        ofredt= (Button) findViewById(R.id.btnofretd);
        ofredt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,viewofrs.class);
                startActivity(i);
            }
        });
        btnofr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             offerInput();
            }
        });

        try
        {
            if (Settings.Secure.getString(this.getContentResolver(),"enabled_notification_listeners").contains(getApplicationContext().getPackageName()))
            {

            } else {
                Toast.makeText(MainActivity.this,"Please Turn on Notification Access for Notifier Admin",Toast.LENGTH_LONG).show();
                startActivity(new Intent(
                        "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            }}
        catch (NullPointerException e){

        }




        final DatabaseHandler db=new DatabaseHandler(this);


        mrecyclerView=(RecyclerView)findViewById(R.id.rv);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        contacts=db.getAllNotify();
        adapter = new AppNotifyAdapter(MainActivity.this,contacts);
        mrecyclerView.setAdapter(adapter);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {



                contacts=db.getAllNotify();
                adapter = new AppNotifyAdapter(MainActivity.this,contacts);
                mrecyclerView.setAdapter(adapter);
                swipeContainer.setRefreshing(false);

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);




        RecyclerView.OnItemTouchListener itemTouchListener = new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }


        };

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener( mrecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {


                                return true;
                            }

                            @Override
                            public boolean canSwipeRight(int position) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                               //  Toast.makeText(MainActivity.this,  " swiped left", Toast.LENGTH_SHORT).show();

                                for (int position : reverseSortedPositions) {
//
                                   Log.d("Noti","Position\t"+position);
                                    try {
                                        Notify cn = contacts.get(position);
                                        db.deleteNotify(cn.getHour(), cn.getMinit(), cn.getSecond(), cn.getDate(),cn.getText());

                                        contacts.remove(position);
                                        adapter.notifyItemRemoved(position);

                                        Toast.makeText(MainActivity.this,  "Notification Deleted", Toast.LENGTH_SHORT).show();
                                    }catch (Exception e){
                                        Log.d("Noti","Exception\t"+e);
                                    }

                                    }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
//                                    Toast.makeText(MainActivity.this, mItems.get(position) + " swiped right", Toast.LENGTH_SHORT).show();
                            Notify  cn = contacts.get(position);
                                    final String pakage=cn.getPackage();
                                    final String noti=cn.getApp();
                                    final String des=cn.getText();
                                    final String app="Other Apps";
                                    Drawable icon = null;
                                    String image = null;

                                    try {
                                        icon = MainActivity.this.getPackageManager().getApplicationIcon(cn.getPackage());
                                        img.setImageDrawable(icon);
                                        // img.buildDrawingCache();
                                        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
                                        Bitmap bitmap = drawable.getBitmap();
                                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                                        byte[] bb = bos.toByteArray();
                                        image = Base64.encodeToString(bb,0);

                                    } catch (PackageManager.NameNotFoundException e) {
                                        Log.d("Noti",e+"");
                                    }

                                    final String finalImage = image;
                                    StringRequest postRequest = new StringRequest(Request.Method.POST,UPLOAD_URL,
                         new Response.Listener<String>() {

                                @Override
                                   public void onResponse(String response) {

                                   Toast.makeText(getApplicationContext(),
                                           "Successfully Posted",
                                          Toast.LENGTH_SHORT).show();
                                          ins=true;

                                        }
                                  }, new Response.ErrorListener() {
                                    @Override
                                 public void onErrorResponse(VolleyError error) {
                                          Log.d("Noti",error+"");
                                  Toast.makeText(getApplicationContext(),
                                         "failed to insert", Toast.LENGTH_SHORT).show();
                               }
                                    }) {
                                    @Override
                            protected Map<String, String> getParams() {

                             // String uploadImage = getStringImage(bitmap);
                               Map<String, String> params = new HashMap<String, String>();

                                        params.put("icon", finalImage);
                                        params.put("package",pakage);
                                        params.put("app_name", noti);
                                        params.put("des", des);
                                        params.put("app_type",app);

                                      return params;
                                 }
                                 };

                              AppController.getInstance().addToRequestQueue(postRequest);

                                    Log.d("Noti","Position\t"+position);
                                    if (ins) {
                                        try {

                                            db.deleteNotify(cn.getHour(), cn.getMinit(), cn.getSecond(), cn.getDate(), cn.getText());

                                            contacts.remove(position);
                                            adapter.notifyItemRemoved(position);


                                        } catch (Exception e) {
                                            Log.d("Noti", "Exception\t" + e);
                                        }
                                    }
                                    ins=false;
                                }

                                adapter.notifyDataSetChanged();
                            }
                        });

        mrecyclerView.addOnItemTouchListener(swipeTouchListener);
    }
    public void offerInput(){


        LayoutInflater inflater= LayoutInflater.from(MainActivity.this);
        View view=inflater.inflate(R.layout.offer_dialog,null);
        edofrname =(EditText)view.findViewById(R.id.ofrname);
        edofrrate=(EditText)view.findViewById(R.id.ofrrate);
        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);

        builder.setView(view);
        builder.setCancelable(false);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                          String ofrname=edofrname.getText().toString();
                          String ofrrate=edofrrate.getText().toString();
                if(ofrname.isEmpty()||ofrrate.isEmpty()){
                     Toast.makeText(MainActivity.this,"Fill all Details",Toast.LENGTH_SHORT).show();
                }else {
                    updateofr(ofrname, ofrrate);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
            }
        });
        builder.show();


    }
    public void updateofr(String name,String rate){

       this.ofrname=name;
        this.ofrrate=rate;

        StringRequest postRequest = new StringRequest(Request.Method.POST,URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),
                                "Successfully Updated",
                                Toast.LENGTH_SHORT).show();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Noti",error+"");
                Toast.makeText(getApplicationContext(),
                        "failed to Update", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                // String uploadImage = getStringImage(bitmap);
                Map<String, String> params = new HashMap<String, String>();

                params.put("offer_name",ofrname);
                params.put("offer_rate",ofrrate);


                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(postRequest);

    }


}


/*    ImageView iv1 = (ImageView)findViewById(R.id.imageView1);
iv1.buildDrawingCache();

*/




