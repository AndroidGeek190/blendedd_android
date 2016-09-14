package com.erginus.blendedd.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.erginus.blendedd.R;
import com.erginus.blendedd.UserHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nazer on 11/3/2015.
 */
public class User_Personal_Setting extends Fragment implements View.OnClickListener {
    String user_fname, user_lname, user_facebook, user_twitter, user_linkedin, user_instagram, user_address_line_1, user_address_line_2, user_zipcode, user_email;
    long user_id;
    String user_security_hash,user_id_updated,credit_card_number;
    EditText edit_first_name, edit_last_name, edit_facebook, edit_twitter, edit_linkedin, edit_instagram, edit_address_one, edit_address_two, edit_zipcode, edit_email;
    Spinner spinner_country, spinner_state, spinner_city;
    CheckBox chk_phone, chk_text;
    String country_name, country_slug, state_name, city_name;
    List<CategoryModel> country_list;
    List<CategoryModel> state_list;
    static List<CategoryModel> city_list;
    CountryListAdapter countryListAdapter;
    StateListAdapter stateListAdapter;
    CityListAdapter cityListAdapter;
    int country_id, state_id, city_id, selected_country_id, selected_state_id, selected_city_id;
    TextView btn_update;
    SharedPreference getdata;
    public static SharedPreferences preferences;

    CheckBox email, phone, text;
    int value_phone,value_text,value_email;

    public User_Personal_Setting() {
//Default Constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_personal_setting, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getdata = new SharedPreference(getActivity());
        user_id = Long.parseLong(getdata.getuserid());
        user_security_hash = getdata.gethashcode();
        user_fname = getdata.getfirstname();
        user_lname = getdata.getlastname();
        user_address_line_1 = getdata.getaddressone();
        user_address_line_2 = getdata.getaddresstwo();
        user_zipcode = getdata.getzipcode();

        Log.e("user id", "" + user_id);
        Log.e("user hashcode", "" + user_security_hash);
        Log.e("user first name", "" + user_fname);
        Log.e("user last name", "" + user_lname);
        Log.e("address 1", "" + user_address_line_1);
        Log.e("address 2", "" + user_address_line_2);
        Log.e("zipcode", "" + user_zipcode);


        edit_first_name = (EditText) rootView.findViewById(R.id.edittext_fname);
        edit_last_name = (EditText) rootView.findViewById(R.id.editText_lname);
        edit_facebook = (EditText) rootView.findViewById(R.id.editText_facebookurl);
        edit_twitter = (EditText) rootView.findViewById(R.id.editText_twitterurl);
        edit_linkedin = (EditText) rootView.findViewById(R.id.editText_linkurl);
        edit_instagram = (EditText) rootView.findViewById(R.id.editText_instagram);
        edit_address_one = (EditText) rootView.findViewById(R.id.editText_addressone);
        edit_address_two = (EditText) rootView.findViewById(R.id.editText_addresstwo);
        edit_zipcode = (EditText) rootView.findViewById(R.id.edt_zipcode);
        spinner_country = (Spinner) rootView.findViewById(R.id.spinner_country);
        spinner_state = (Spinner) rootView.findViewById(R.id.spinner_state);
        spinner_city = (Spinner) rootView.findViewById(R.id.spinner_city);
        chk_phone = (CheckBox) rootView.findViewById(R.id.checkBox_phone);
        chk_text = (CheckBox) rootView.findViewById(R.id.checkBox_text);
        email = (CheckBox) rootView.findViewById(R.id.checkBox_email);
        phone = (CheckBox) rootView.findViewById(R.id.checkBox_phone);
        text = (CheckBox) rootView.findViewById(R.id.checkBox_text);
        btn_update = (TextView) rootView.findViewById(R.id.button_update);

        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        text.setOnClickListener(this);

        edit_first_name.setText(user_fname);
        edit_last_name.setText(user_lname);
        edit_address_one.setText(user_address_line_1);
        edit_address_two.setText(user_address_line_2);
        edit_zipcode.setText(user_zipcode);


        country_list = new ArrayList<>();
        state_list = new ArrayList<>();
        city_list = new ArrayList<>();
        GetCountry();
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_fname = edit_first_name.getText().toString();
                user_lname = edit_last_name.getText().toString();
                user_facebook = edit_facebook.getText().toString();
                user_linkedin = edit_linkedin.getText().toString();
                user_twitter = edit_twitter.getText().toString();
                user_instagram = edit_instagram.getText().toString();
                user_address_line_1 = edit_address_one.getText().toString();
                user_address_line_2 = edit_address_two.getText().toString();
                user_zipcode = edit_zipcode.getText().toString();

                boolean Update = false;
                View focusView = null;
                if (TextUtils.isEmpty(user_fname)) {
                    edit_first_name.setError("Fill First Name");
                    focusView = edit_first_name;
                    Update = true;
                }
                if (TextUtils.isEmpty(user_lname)) {
                    edit_last_name.setError("Fill Last Name");
                    focusView = edit_last_name;
                    Update = true;
                }

                if (TextUtils.isEmpty(user_address_line_1)) {
                    edit_address_one.setError("Fill Address");
                    focusView = edit_address_one;
                    Update = true;
                }
                if (TextUtils.isEmpty(user_address_line_2)) {
                    edit_address_two.setError("Fill Address");
                    focusView = edit_address_two;
                    Update = true;
                }

                if (TextUtils.isEmpty(user_zipcode)) {
                    edit_zipcode.setError("Fill Zipcode");
                    focusView = edit_zipcode;
                    Update = true;
                }

                if (Update) {
                    // error in login
                    focusView.requestFocus();
                } else {
                    preferences  = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("user_first_name");
                    editor.remove("user_last_name");
                    editor.remove("user_address_line_1");
                    editor.remove("user_address_line_2");
                    editor.remove("user_zipcode");
                    editor.commit();
                    Update();
                }
            }
        });

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
        return rootView;
    }

    public List<CategoryModel> getcountrylist() {
        return country_list;
    }

    public void setCountrylist(List<CategoryModel> countrylist) {
        this.country_list = countrylist;
    }

    public List<CategoryModel> getstatelist() {
        return state_list;
    }

    public void setstatelist(List<CategoryModel> statelist) {
        this.state_list = statelist;
    }

    public List<CategoryModel> getcitylist() {
        return city_list;
    }

    public void setcitylist(List<CategoryModel> citylist) {
        this.city_list = citylist;
    }

    public void GetCountry() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                       // Toast.makeText(getActivity(), "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    // Log.e("i am here at adapter", ""+jsonArray);
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
                                countryListAdapter = new CountryListAdapter(getActivity(), country_list);
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
                        Toast.makeText(getActivity(), "Timeout Error",
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
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CategoryModel categoryModel(int id, String name, String slug) {
        CategoryModel model = new CategoryModel();
        model.setCountry_id(id);
        model.setCountry_name(name);
        model.setCountry_slug(slug);

        return model;
    }


    public void GetState() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "states");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "states", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    ;
               //     Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                       // Toast.makeText(getActivity(), "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
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
                                stateListAdapter = new StateListAdapter(getActivity(), state_list);
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
                    ;
                    //  VolleyLog.d("", "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getActivity(), "Timeout Error",
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

                    params.put("countries_id", "" + selected_country_id);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CategoryModel categoryModelstate(int stateid, String statename) {
        CategoryModel model = new CategoryModel();
        model.setstate_id(stateid);
        model.setstate_name(statename);

        return model;
    }

    public void GetCity() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
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
                       // Toast.makeText(getActivity(), "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
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
                                cityListAdapter = new CityListAdapter(getActivity(), city_list);
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
                        Toast.makeText(getActivity(), "Timeout Error",
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

                    params.put("states_id", "" + selected_state_id);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CategoryModel categoryModel(int id, String name) {
        CategoryModel model = new CategoryModel();
        model.setcountry_city_id(id);
        model.setcountry_city_name(name);

        return model;
    }

    public void Update() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "edit_profile");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "edit_profile", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.e("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(getActivity(), "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONObject object1 = object.getJSONObject("data");
                                    user_fname=object1.getString("user_first_name");
                                    user_lname=object1.getString("user_last_name");
                                    user_address_line_1=object1.getString("user_address_line_1");
                                    user_address_line_2=object1.getString("user_address_line_2");
                                    user_zipcode=object1.getString("user_zipcode");


                                    setPreferences(user_fname,user_lname,user_address_line_1,user_address_line_2,user_zipcode);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(getActivity(), UserHomeActivity.class);
                            startActivity(intent);


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
                        Toast.makeText(getActivity(), "Timeout Error",
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

                    params.put("user_id", "" + user_id);
                    params.put("user_security_hash", user_security_hash);
                    params.put("user_first_name", user_fname);
                    params.put("user_last_name", user_lname);
                    params.put("user_facebook_url", user_facebook);
                    params.put("user_twitter_url", user_twitter);
                    params.put("user_linkedin_url", user_linkedin);
                    params.put("user_instagram_url", user_linkedin);
                    params.put("user_instagram_url", user_instagram);
                    params.put("user_address_line_1", user_address_line_1);
                    params.put("user_address_line_2", user_address_line_2);
                    params.put("countries_id", "" + selected_country_id);
                    params.put("states_id", "" + selected_state_id);
                    params.put("cities_id", "" + selected_city_id);
                    params.put("user_zipcode", user_zipcode);
                    params.put("user_communication_via_email",""+value_email);
                    params.put("user_communication_via_phone_call",""+value_phone);
                    params.put("user_communication_via_sms",""+value_text);


                    Log.e("user id", "" + user_id);
                    Log.e("user security hash", user_security_hash);
                    Log.e("user_addaress line 1", user_address_line_1);
                    Log.e("user address line 2", user_address_line_2);
                    Log.e("countries id", "" + selected_country_id);
                    Log.e("State_id", "" + selected_state_id);
                    Log.e("cities_id", "" + selected_city_id);
                    Log.e("user zipcode", user_zipcode);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public SharedPreference setPreferences(String fname,String lname,String addressone,String addresstwo,String zipcode)
    {
        SharedPreference data=new SharedPreference(getActivity());
        data.setfirstname(fname);
        data.setlastname(lname);
        data.setaddressone(addressone);
        data.setaddresstwo(addresstwo);
        data.setzipcode(zipcode);

        return data;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.checkBox_email:
                if (email.isChecked()) {
                    value_email = 1;
                }
                else{
                    value_email=0;
                }
            case R.id.checkBox_phone:
                if (phone.isChecked()) {
                    value_phone = 1;

                } else {
                    value_phone = 0;
                }
                break;

            case R.id.checkBox_text:
                if (text.isChecked()) {
                    value_text = 1;
                } else {
                    value_text = 0;
                }
                break;
            default:
        }
    }
}
