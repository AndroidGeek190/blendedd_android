package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommunicationPrefrencesActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title, btn_continue;
    LinearLayout ll_navigation;
    CheckBox email, phone, text, optioncheckbox, acceptcheckbox;
    int value_phone, value_text, value_optioncheckbox, value_accept;
    String user_security_hash;
    long user_id;
    SharedPreference getdata;
    public static SharedPreferences preferences;
    String user_first_name,user_last_name,user_login,user_login_password,confirm_user_login_password,user_email,user_primary_contact,
            user_dob,user_address_line_1,user_address_line_2,user_zipcode,user_credit_card_name,
            user_credit_card_number,user_credit_card_cvv,bank_name,bank_number,bank_route;
    int user_credit_card_expiry_month,user_credit_card_expiry_year;
    int countries_id,states_id,cities_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication_prefrences);

        getdata = new SharedPreference(CommunicationPrefrencesActivity.this);
//        user_id = Long.parseLong(getdata.getuserid());
//        user_security_hash = getdata.gethashcode();
        user_first_name=getdata.getfirstname();user_last_name=getdata.getlastname();user_login=getdata.getuserloginname();
        user_login_password=getdata.getpassword();confirm_user_login_password=getdata.getconfirmpassword();user_email=getdata.getemail();
        user_primary_contact=getdata.getcontactnumber(); user_dob=getdata.getbday();user_address_line_1=getdata.getaddressone();
        user_address_line_2=getdata.getaddresstwo();
        countries_id= getdata.getcountryid();
        states_id= getdata.getstateid();
        cities_id=getdata.getcityid();
        user_zipcode=getdata.getzipcode();
        user_credit_card_name=getdata.getcardname();
        user_credit_card_number=getdata.getcreditcardnumber();
        user_credit_card_expiry_month=getdata.getcardexpirymonth();
        user_credit_card_expiry_year=getdata.getcardexpiryyear();
        user_credit_card_cvv=getdata.getcardcvv();

       bank_name= getdata.getBankname();
        bank_number=getdata.getBank_Account_number();
        bank_route=getdata.getBank_route_number();


        email = (CheckBox) findViewById(R.id.checkBox_email);
        phone = (CheckBox) findViewById(R.id.checkBox_phone);
        optioncheckbox = (CheckBox) findViewById(R.id.checkBox);
        text = (CheckBox) findViewById(R.id.checkBox_text);
        acceptcheckbox = (CheckBox) findViewById(R.id.checkBox_1);

        email.setOnClickListener(this);
        phone.setOnClickListener(this);
        text.setOnClickListener(this);
        optioncheckbox.setOnClickListener(this);
        acceptcheckbox.setOnClickListener(this);


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtVw_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtVw_title.setVisibility(View.GONE);
        txt_title = (TextView) toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);
        img_back = (ImageView) toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        ll_navigation = (LinearLayout) toolbar.findViewById(R.id.ll_navi);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        txt_title.setText("Communication Prefrences");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_continue = (TextView) findViewById(R.id.button_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value_accept == 0) {
                    Toast.makeText(CommunicationPrefrencesActivity.this, "Please Accept the Agreement and Policies to Registered", Toast.LENGTH_LONG).show();
                } else {
                    save_information();
                }
            }

        });

    }

    public void save_information() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(CommunicationPrefrencesActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "signup_step_four");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "signup_step_four", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();

                  //  Log.d("", ".......response====" + response.toString());

                    ////////
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(CommunicationPrefrencesActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    user_security_hash=object.getString("user_security_hash");
                                    user_id= Long.parseLong(object.getString("user_id"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //getdata.cleardata();

                            preferences  = PreferenceManager.getDefaultSharedPreferences(CommunicationPrefrencesActivity.this);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.remove("user_first_name");
                            editor.remove("user_last_name");
                            editor.remove("user_email");
                            editor.remove("user_primary_contact");
                            editor.remove("user_login");
                            editor.remove("user_dob");
                            editor.remove("user_login_password");
                            editor.remove("confirm_user_login_password");
                            editor.remove("user_id");
                            editor.remove("user_security_hash");
                            editor.remove("user_address_line_1");
                            editor.remove("user_address_line_2");
                            editor.remove("user_zipcode");
                            editor.remove("countries_id");
                            editor.remove("state_id");
                            editor.remove("city_id");
                            editor.remove("user_credit_card_name");
                            editor.remove("user_credit_card_number");
                            editor.remove("user_credit_card_expiry_month");
                            editor.remove("user_credit_card_expiry_year");
                            editor.remove("user_credit_card_cvv");
                            editor.commit();

                            Intent intent = new Intent(CommunicationPrefrencesActivity.this, HomeActivity.class);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(CommunicationPrefrencesActivity.this, "Timeout Error",
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
                    params.put("user_first_name", user_first_name);
                    params.put("user_last_name", user_last_name);
                    params.put("user_login", user_login);
                    params.put("user_login_password", user_login_password);
                    params.put("confirm_user_login_password", confirm_user_login_password);
                    params.put("user_email", user_email);
                    params.put("user_primary_contact", user_primary_contact);
                    params.put("user_dob", user_dob);

                    params.put("user_address_line_1", user_address_line_1);
                    params.put("user_address_line_2", user_address_line_2);
                    params.put("countries_id", ""+countries_id);
                    params.put("states_id", ""+states_id);
                    params.put("cities_id", ""+cities_id);
                    params.put("user_zipcode", user_zipcode);

                    params.put("user_credit_card_name",user_credit_card_name);
                    params.put("user_credit_card_number",user_credit_card_number);
                    params.put("user_credit_card_expiry_month",""+user_credit_card_expiry_month);
                    params.put("user_credit_card_expiry_year",""+user_credit_card_expiry_year);
                    params.put("user_credit_card_cvv", user_credit_card_cvv);

                    params.put("user_credit_card_name",bank_name);
                    params.put("user_credit_card_number",bank_number);
                    params.put("user_credit_card_cvv", bank_route);

                    params.put("user_communication_via_phone_call", "" + value_phone);
                    params.put("user_communication_via_sms", "" + value_text);
                    params.put("user_newsletter_subscription", "" + value_optioncheckbox);
                    params.put("user_agreement", "" + value_accept);


//                    Log.e("Haaaaassssssssh Code", " " + user_security_hash);
//                    Log.e("Ussssseeeerrrrr IDDD", " " + user_id);
//                    Log.e("first name", user_first_name);
//                    Log.e("Last name", user_last_name);
//                    Log.e("user login", user_login);
//                    Log.e("user login password", user_login_password);
//                    Log.e("confirm login password", confirm_user_login_password);
//                    Log.e("user email", user_email);
//                    Log.e("user phone", user_primary_contact);
//                    Log.e("user dob", user_dob);
//
//                    Log.e("user_addaress line 1",user_address_line_1);
//                    Log.e("user address line 2",user_address_line_2);
//                    Log.e("countries id", "" +countries_id);
//                    Log.e("State_id",""+states_id);
//                    Log.e("cities_id",""+cities_id);
//                    Log.e("user zipcode",user_zipcode);
//
//
//                    Log.e("phone_call", "" + value_phone);
//                    Log.e("communication_via_sms", "" + value_text);
//                    Log.e("newsletter_subscription", "" + value_optioncheckbox);
//                    Log.e("user_agreement", "" + value_accept);

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(CommunicationPrefrencesActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
            case R.id.checkBox:
                if (optioncheckbox.isChecked()) {
                    value_optioncheckbox = 1;
                } else {
                    value_optioncheckbox = 0;
                }
            case R.id.checkBox_1:
                if (acceptcheckbox.isChecked()) {
                    value_accept = 1;
                } else {
                    value_accept = 0;
                }
            default:

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        onResume();
    }
}
