package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
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

public class AddressActivity extends AppCompatActivity {
    TextView btn_continue;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
    EditText address1,address2,zipcode;
    String user_address1,user_address2,user_zipcode;
    long user_id;
    String user_security_hash;
    String  country_name,country_slug,state_name,city_name;
    List<CategoryModel> country_list;
    List<CategoryModel> state_list;
    List<CategoryModel> city_list;
    Spinner spinner_state,spinner_country,spinner_city;
    int country_id,state_id,city_id,selected_country_id, selected_state_id,selected_city_id;
    CountryListAdapter countryListAdapter;
    StateListAdapter stateListAdapter;
    CityListAdapter cityListAdapter;
    SharedPreference getdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getdata=new SharedPreference(AddressActivity.this);

        //user_id= Long.parseLong(getdata.getuserid());
        //user_security_hash=getdata.gethashcode();

        Log.e("hash Code it is", " " + user_id);
        Log.e("User id is", " " + user_security_hash);


        btn_continue=(TextView)findViewById(R.id.button_continue);
        address1=(EditText)findViewById(R.id.editText_address);
        address2=(EditText)findViewById(R.id.editText_address2);

        zipcode=(EditText)findViewById(R.id.edt_zipcode);
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
               // Log.e("countryid", "" + selected_country_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_state_id = state_list.get(position).getstate_id();
               // Log.e("selected state", " " + selected_state_id);
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
           //     Log.e("selected city", " " + selected_city_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
        txt_title.setText("Address");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_address1 =address1.getText().toString();
                user_address2=address2.getText().toString();
                user_zipcode=zipcode.getText().toString();

                boolean Address = false;
                View focusView = null;

                if (TextUtils.isEmpty(user_address1)) {
                    address1.setError("Fill Address");
                    focusView = address1;
                    Address = true;
                }
//                if (TextUtils.isEmpty(user_address2)) {
//                    address2.setError("Fill Address");
//                    focusView = address2;
//                    Address = true;
//                }

                if (TextUtils.isEmpty(user_zipcode)) {
                    zipcode.setError("Fill Zipcode");
                    focusView = zipcode;
                    Address = true;
                }

                if (Address) {
                    // error in login
                    focusView.requestFocus();
                }

                else
                {
                    Address();
//                    Intent intent = new Intent(AddressActivity.this, CreditCardInfoActivity.class);
//                     startActivity(intent);
//                    overridePendingTransition(0, 0);
                }
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
            final ProgressDialog pDialog = new ProgressDialog(AddressActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "countries");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "countries", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                   // Log.d("", ".......response====" + response.toString());

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
                                countryListAdapter = new CountryListAdapter(AddressActivity.this, country_list);
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
                        Toast.makeText(AddressActivity.this, "Timeout Error",
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
            VolleySingleton.getInstance(AddressActivity.this.getApplicationContext()).addToRequestQueue(sr);


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
            final ProgressDialog pDialog = new ProgressDialog(AddressActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "states");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "states", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                  //  Log.d("", ".......response====" + response.toString());

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
                                stateListAdapter = new StateListAdapter(AddressActivity.this, state_list);
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
                        Toast.makeText(AddressActivity.this, "Timeout Error",
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
            VolleySingleton.getInstance(AddressActivity.this.getApplicationContext()).addToRequestQueue(sr);


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
            final ProgressDialog pDialog = new ProgressDialog(AddressActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "cities");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "cities", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                  //  Log.d("", ".......response====" + response.toString());

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
                                cityListAdapter = new CityListAdapter(AddressActivity.this, city_list);
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
                        Toast.makeText(AddressActivity.this, "Timeout Error",
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
            VolleySingleton.getInstance(AddressActivity.this.getApplicationContext()).addToRequestQueue(sr);


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
    public void Address()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(AddressActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "signup_step_two");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"signup_step_two", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                  //  Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(AddressActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONObject object1 = object.getJSONObject("data");


                                }
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            getdata.setaddressone(user_address1);
                            getdata.setaddresstwo(user_address2);
                            getdata.setzipcode(user_zipcode);
                            getdata.setcountryid(selected_country_id);
                            getdata.setstateid(selected_state_id);
                            getdata.setcityid(selected_city_id);
//                            if(getdata.getusertype().equalsIgnoreCase("1")) {
//                                Intent intent = new Intent(AddressActivity.this, CreditCardInfoActivity.class);
//                                startActivity(intent);
//                                overridePendingTransition(0, 0);
//                            }
//                            else if(getdata.getusertype().equalsIgnoreCase("2"))
//                            {
//                                Intent intent = new Intent(AddressActivity.this, Activity_Bank_Account_Info.class);
//                                startActivity(intent);
//                                overridePendingTransition(0, 0);
//                            }
//                            else {
                                Intent intent = new Intent(AddressActivity.this, CreditCardInfoActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
//                            }

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
                        Toast.makeText(AddressActivity.this, "Timeout Error",
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

//                    params.put("user_id","" +user_id);
//                    params.put("user_security_hash", user_security_hash);
                    params.put("user_address_line_1", user_address1);
                    params.put("user_address_line_2", user_address2);
                    params.put("countries_id", ""+selected_country_id);
                    params.put("states_id", ""+selected_state_id);
                    params.put("cities_id", ""+selected_city_id);
                    params.put("user_zipcode", user_zipcode);

//                    Log.e("user id",""+user_id);
//                    Log.e("user security hash", user_security_hash);
//                    Log.e("user_addaress line 1", user_address1);
//                    Log.e("user address line 2", user_address2);
//                    Log.e("countries id", "" +selected_country_id);
//                    Log.e("State_id",""+selected_state_id);
//                    Log.e("cities_id",""+selected_city_id);
//                    Log.e("user zipcode",user_zipcode);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(AddressActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        onResume();
        //super.onBackPressed();
    }
    public SharedPreference setPreferences(String addressone,String addresstwo,String zipcode)
    {
        SharedPreference data=new SharedPreference(AddressActivity.this);

        data.setaddressone(addressone);
        data.setaddresstwo(addresstwo);
        data.setzipcode(zipcode);
        return data;
    }
}
