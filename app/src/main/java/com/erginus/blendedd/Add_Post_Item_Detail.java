package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.erginus.blendedd.Adapter.CityListAdapter;
import com.erginus.blendedd.Adapter.CountryListAdapter;
import com.erginus.blendedd.Adapter.StateListAdapter;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nazer on 2/6/2016.
 */
public class Add_Post_Item_Detail extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
EditText street,cross_street,zip,post_title,post_desc;
    TextView submit;

    String  country_name,country_slug,state_name,city_name;
    List<CategoryModel> country_list;
    List<CategoryModel> state_list;
    List<CategoryModel> city_list;
    int country_id,state_id,city_id,selected_country_id, selected_state_id,selected_city_id;
    CountryListAdapter countryListAdapter;
    StateListAdapter stateListAdapter;
    CityListAdapter cityListAdapter;
    SharedPreference getdata;
    Spinner spinner_state,spinner_country,spinner_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_item_detail);

        submit=(TextView)findViewById(R.id.button_continue);

        street=(EditText)findViewById(R.id.edit_street);
        cross_street=(EditText)findViewById(R.id.edit_cross_street);
        zip=(EditText)findViewById(R.id.edit_zipcode);
        post_title=(EditText)findViewById(R.id.edit_post_title);
        post_desc=(EditText)findViewById(R.id.edit_post_description);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
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

        spinner_country =(Spinner)findViewById(R.id.spinner_country);
        spinner_state=(Spinner)findViewById(R.id.spinner_state);
        spinner_city=(Spinner)findViewById(R.id.spinner_city);

        country_list=new ArrayList<>();
        state_list=new ArrayList<>();
        city_list=new ArrayList<>();
        GetCountry();

        spinner_country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_country_id = country_list.get(position).getCountry_id();
                GetState();
                Log.e("countryid", "" + selected_country_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_state_id = state_list.get(position).getstate_id();
                Log.e("selected state", " " + selected_state_id);
                city_list.clear();
                GetCity();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_city_id = city_list.get(position).getcountry_city_id();
                Log.e("selected city", " " + selected_city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        txt_title.setText("Add Post Details");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_Post_Item_Detail.this, Add_Post_Rates.class);
                startActivity(intent);
            }
        });
    }
    public List<CategoryModel> getcountrylist() {
        return country_list;
    }

    public void setCountrylist(List<CategoryModel> countrylist) {
        this.country_list = countrylist;
    }

    public List<CategoryModel> getstatelist(){
        return state_list;
    }
    public void setstatelist(List<CategoryModel> statelist) {
        this.state_list = statelist;
    }
    public List<CategoryModel> getcitylist(){
        return city_list;
    }
    public void setcitylist(List<CategoryModel> citylist) {
        this.city_list = citylist;
    }
    public void GetCountry() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(Add_Post_Item_Detail.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "countries");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "countries", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        // Toast.makeText(AddressActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    Log.e("i am here at adapter", ""+jsonArray);
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            country_id = Integer.parseInt(jsonObject.getString("country_id"));
                                            country_name = jsonObject.getString("country_name");
                                            country_slug = jsonObject.getString("country_slug");
                                            country_list.add(categoryModel(country_id, country_name, country_slug));

                                        }
                                    }
                                    setCountrylist(country_list);
                                }
                                countryListAdapter = new CountryListAdapter(Add_Post_Item_Detail.this, country_list);
                                spinner_country.setAdapter(countryListAdapter);

                            } catch (Exception e) {
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
                        Toast.makeText(Add_Post_Item_Detail.this, "Timeout Error",
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

                    params.put("current_timestamp", "1");
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(Add_Post_Item_Detail.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CategoryModel categoryModel(int id, String name, String slug )
    {
        CategoryModel model=new CategoryModel();
        model.setCountry_id(id);
        model.setCountry_name(name);
        model.setCountry_slug(slug);

        return  model;
    }


    public void GetState()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(Add_Post_Item_Detail.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "states");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "states", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        //Toast.makeText(AddressActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    Log.e("i am here at State", ""+jsonArray);
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            state_id = Integer.parseInt(jsonObject.getString("state_id"));
                                            state_name = jsonObject.getString("state_name");
                                            state_list.add(categoryModelstate(state_id, state_name));

                                        }
                                    }
                                    setstatelist(state_list);
                                }
                                stateListAdapter = new StateListAdapter(Add_Post_Item_Detail.this, state_list);
                                spinner_state.setAdapter(stateListAdapter);

                            } catch (Exception e) {
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
                    //  VolleyLog.d("", "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(Add_Post_Item_Detail.this, "Timeout Error",
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

                    params.put("countries_id", ""+selected_country_id);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(Add_Post_Item_Detail.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private CategoryModel categoryModelstate(int stateid, String statename)
    {
        CategoryModel model=new CategoryModel();
        model.setstate_id(stateid);
        model.setstate_name(statename);

        return  model;
    }

    public void GetCity()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(Add_Post_Item_Detail.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "cities");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "cities", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        // Toast.makeText(AddressActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    Log.e("i am here at City", ""+jsonArray);
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            city_id = Integer.parseInt(jsonObject.getString("city_id"));
                                            city_name = jsonObject.getString("city_name");
                                            city_list.add(categoryModel(city_id, city_name));

                                        }
                                    }
                                    setcitylist(city_list);
                                }
                                cityListAdapter = new CityListAdapter(Add_Post_Item_Detail.this, city_list);
                                spinner_city.setAdapter(cityListAdapter);

                            } catch (Exception e) {
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
                        Toast.makeText(Add_Post_Item_Detail.this, "Timeout Error",
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

                    params.put("states_id",""+selected_state_id);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(Add_Post_Item_Detail.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private CategoryModel categoryModel(int id, String name)
    {
        CategoryModel model=new CategoryModel();
        model.setcountry_city_id(id);
        model.setcountry_city_name(name);

        return  model;
    }
}