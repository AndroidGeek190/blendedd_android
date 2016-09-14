package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.erginus.blendedd.Adapter.ActivityListAdapter;
import com.erginus.blendedd.Adapter.CategoryItemsAdapter;
import com.erginus.blendedd.CategoryModel.ActivityModel;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.Prefshelper;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserActivity extends AppCompatActivity {
    List<ActivityModel> activity;
    ListView listView;
    LinearLayout ll_navigation;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    SharedPreference getdata;
    List<ActivityModel> list;
    ActivityListAdapter activityListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtVw_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        txtVw_title.setVisibility(View.GONE);
        txt_title=(TextView)toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);
        img_back=(ImageView)toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        ll_navigation=(LinearLayout)toolbar.findViewById(R.id.ll_navi);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        txt_title.setText("User Activity");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getdata = new SharedPreference(this);
        list=new ArrayList<>();

        activity=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);

    }
    public void categoriesItemList(final String category_id)
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(UserActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "dashboard");

            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"dashboard", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    ;
                    Log.d("", ".......response====" + response.toString());

                    ////////
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                          Toast.makeText(UserActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {
                                int offset=0;
                                String id,desc,ordr_price, ordr_dt, image;
                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    if(jsonArray.length()>0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                                            id= jsonObject.getString("posts_id");
                                            image=jsonObject.getString("post_image_url");
                                            desc=jsonObject.getString("post_description");
                                            ordr_price=jsonObject.getString("post_display_price");
                                            ordr_dt=jsonObject.getString("invoice_created");

                                            list.add(activityModel(id, image, desc, ordr_price, ordr_dt));

                                        }
                                    }
                                    setList(list);
                                }
                                if(listView.getAdapter()==null){
                                    activityListAdapter = new ActivityListAdapter(UserActivity.this, list);
                                    listView.setAdapter(activityListAdapter);

                                }
                                else {
                                    activityListAdapter.notifyDataSetChanged();

                                }


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    ;
                    //  VolleyLog.d("", "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(UserActivity.this, "Timeout Error",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ServerError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof NetworkError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ParseError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("user_id", getdata.getuserid());
                    params.put("user_security_hash", getdata.gethashcode());

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(UserActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public List<ActivityModel> getList() {
        return list;
    }

    public void setList(List<ActivityModel> list) {
        this.list = list;
    }
    private ActivityModel activityModel(String id, String image, String desc, String price, String date)
    {
        ActivityModel model=new ActivityModel();
        model.setPostId(id);
        model.setImage(image);
        model.setPostDescription(desc);
        model.setPrice(price);
        model.setDate(date);

        return  model;
    }
    @Override
    public void onBackPressed() {

    }

}
