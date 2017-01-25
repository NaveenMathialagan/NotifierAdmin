package com.example.naveenkumar.notifieradmin.offers;

import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.naveenkumar.notifieradmin.AppController;

import com.example.naveenkumar.notifieradmin.R;
import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class viewofrs extends AppCompatActivity {
    SwipeRefreshLayout swipeContainer;
   public RecyclerView mrecyclerView;
    OfrAdapter adapter;
    List<ofrinst> ofrinsts;
    List<Post> posts;
    final String URL_JSON = "http://www.thecityshoppers.com/offer.php";

    private static String URL="http://www.thecityshoppers.com/deleteoffer.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewofrs);

        mrecyclerView=(RecyclerView)findViewById(R.id.rvofr);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainerofr);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                call_api(URL_JSON);
                swipeContainer.setRefreshing(false);

            }
        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        call_api(URL_JSON);

        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener( mrecyclerView,
                        new SwipeableRecyclerViewTouchListener.SwipeListener() {
                            @Override
                            public boolean canSwipeLeft(int position) {


                                return false;
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


                                }


                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                              //  Toast.makeText(viewofrs.this,  " swiped right", Toast.LENGTH_SHORT).show();
                                    ofrinst  ofr = ofrinsts.get(position);
                                    final String ofr_name=ofr.getOfrname();
                                    final String ofr_rate=ofr.getOfrrate();
                                   StringRequest postRequest = new StringRequest(Request.Method.POST,URL,
                                            new Response.Listener<String>() {

                                                @Override
                                                public void onResponse(String response) {

                                                    Toast.makeText(getApplicationContext(),
                                                            "Successfully Deleted",
                                                            Toast.LENGTH_SHORT).show();


                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.d("Noti",error+"");
                                            Toast.makeText(getApplicationContext(),
                                                    "failed to Delete", Toast.LENGTH_SHORT).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {

                                            // String uploadImage = getStringImage(bitmap);
                                            Map<String, String> params = new HashMap<String, String>();

                                           params.put("offer_name", ofr_name);
                                            params.put("offer_rate",ofr_rate);


                                            return params;
                                        }
                                    };

                                    AppController.getInstance().addToRequestQueue(postRequest);
                                    try {



                                        ofrinsts.remove(position);
                                        adapter.notifyItemRemoved(position);


                                    } catch (Exception e) {
                                        Log.d("Noti", "Exception\t" + e);
                                    }

                                    Log.d("Noti","Position\t"+position);

                                }

                                adapter.notifyDataSetChanged();
                            }
                        });
        mrecyclerView.addOnItemTouchListener(swipeTouchListener);

    }



    public void call_api(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.v("Noti", response);



                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        Type fooType = new TypeToken<ArrayList<Post>>() {
                        }.getType();
                        List<Post> posts = gson.fromJson(response, fooType);

                        handlePostsList(posts);

                        adapter = new OfrAdapter(ofrinsts);
                        mrecyclerView.setAdapter(adapter);
                        Log.d("Noti","Adapter setted");

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Something went wrong!");
                Toast.makeText(viewofrs.this, "Check your Internet Connection", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        int socketTimeout = 30000000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

    private void handlePostsList(List<Post> posts) {


        this.posts = posts;

        ofrinsts = new ArrayList<>();

        for (Post post : this.posts) {

           ofrinst item=new ofrinst();

                item.setOfrname(post.offer_name);
                item.setOfrrate(post.offer_rate);
                Log.d("Noti","Working in post\t"+post.offer_name);
                Log.d("Noti","Working in post\t"+post.offer_rate);
                ofrinsts.add(item);
            }
        }

    }


