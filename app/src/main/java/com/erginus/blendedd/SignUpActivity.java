package com.erginus.blendedd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.erginus.blendedd.Commons.MapAppConstant;
//import com.erginus.blendedd.Commons.Prefshelper;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextView btn_continue;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation, ll_dob;
    EditText first_name, last_name, email_id, user_id, user_password, user_confirm_password, contact_no, dob;
    String user_fname, lname, email, user, phnumber, datebirth, password, confirm_password;
    String dateString;
    Spinner day, month, year;
    int cyear;
    ArrayList<String> allyears = new ArrayList<String>();
    ArrayList<String> allmonths = new ArrayList<String>();
    ArrayList<String> alldays = new ArrayList<String>();
    ArrayList<String> users = new ArrayList<String>();
    String user_array[]={"Select User Type","Buyer","seller","Both"};
    String position;
    int selectedyear = 0, selectedday = 0, selectedmonth = 0;
    String date;
    String hashcode;
    long userid;
    SharedPreference getdata;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getdata = new SharedPreference(this);
        first_name = (EditText) findViewById(R.id.edittext_fname);
        last_name = (EditText) findViewById(R.id.editText_lname);
        email_id = (EditText) findViewById(R.id.editText_email);
        user_id = (EditText) findViewById(R.id.editText_userid);
        //ll_dob = (LinearLayout) findViewById(R.id.ll_describstn);
        user_password = (EditText) findViewById(R.id.editText_password);
        user_confirm_password = (EditText) findViewById(R.id.editText_confirm_password);
        contact_no = (EditText) findViewById(R.id.editText_phnno);
       // dob = (EditText) findViewById(R.id.editText_dob);
        day = (Spinner) findViewById(R.id.day);
        month = (Spinner) findViewById(R.id.month);
        year = (Spinner) findViewById(R.id.year);
        //sp_user= (Spinner) findViewById(R.id.user_type);
//        ArrayAdapter<String> user_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, user_array);
//        user_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        sp_user.setAdapter(user_adapter);
//        sp_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                 a = String.valueOf(position);
//                getdata.setusertype(a);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
        datemonth();
//        if (getdata.getloginwith().equalsIgnoreCase("0")) {
//            user_id.setVisibility(View.GONE);
//            user_password.setVisibility(View.GONE);
//            user_confirm_password.setVisibility(View.GONE);
//            dob.setVisibility(View.VISIBLE);
//            ll_dob.setVisibility(View.GONE);
//            first_name.setText(getdata.getfirstname());
//            last_name.setText(getdata.getlastname());
//            email_id.setText(getdata.getemail());
//            dob.setText(getdata.getbday());
//        } else {
//            user_id.setVisibility(View.VISIBLE);
//            user_password.setVisibility(View.VISIBLE);
//            user_confirm_password.setVisibility(View.VISIBLE);
//            dob.setVisibility(View.GONE);
//            ll_dob.setVisibility(View.VISIBLE);
//
//        }
        btn_continue = (TextView) findViewById(R.id.button_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean cancelSignup = false;
                View focusView = null;

                    user_fname = first_name.getText().toString();
                    lname = last_name.getText().toString();
                    email = email_id.getText().toString();
                    user = user_id.getText().toString();
                    password = user_password.getText().toString();
                    confirm_password = user_confirm_password.getText().toString();
                    phnumber = contact_no.getText().toString();


                    if (TextUtils.isEmpty(user_fname)) {
                        first_name.setError("First Name is required");
                        focusView = first_name;
                        cancelSignup = true;
                    }
                    else if (TextUtils.isEmpty(lname)) {
                        last_name.setError("Last Name is required");
                        focusView = last_name;
                        cancelSignup = true;
                    } else if (TextUtils.isEmpty(email)) {
                        email_id.setError("Email is required");
                        focusView = email_id;
                        cancelSignup = true;
                    }
                else    if (TextUtils.isEmpty(user)) {
                    user_id.setError("User ID is required");
                    focusView = user_id;
                    cancelSignup = true;
                }

                else   if (TextUtils.isEmpty(password)) {
                    user_password.setError("Password must have at least 8 characters and contain at least two of the following: uppercase letters, lower case letters, number, and symbols");
                    focusView = user_password;
                    cancelSignup = true;
                }
                else  if (TextUtils.isEmpty(confirm_password)) {
                    user_confirm_password.setError("Confirm Password does not match the Password");
                    focusView = user_confirm_password;
                    cancelSignup = true;
                }
                    else if (TextUtils.isEmpty(phnumber)) {
                        contact_no.setError("Primary Contact Number is required");
                        focusView = contact_no;
                        cancelSignup = true;
                    }
                    else if ((selectedday == 29) && (selectedmonth == 02) && (selectedyear % 4 != 0)) {
                        Toast.makeText(SignUpActivity.this, "Please Select the proper date", Toast.LENGTH_LONG).show();
                    } else if ((selectedday == 30&& (selectedmonth == 02)) || ((selectedday == 31) && (selectedmonth == 02)))

                    {
                        Toast.makeText(SignUpActivity.this, "Please Select the proper date", Toast.LENGTH_LONG).show();

                    }else if(((selectedmonth==4)&&(selectedday==31))||((selectedmonth==6)&&(selectedday==31))||((selectedmonth==9)&&(selectedday==31))||((selectedmonth==11)&&(selectedday==31)))

                    {
                        Toast.makeText(SignUpActivity.this, "Please Select the proper date", Toast.LENGTH_LONG).show();
                    }
//                    else if (a.equalsIgnoreCase("0")) {
//                        Toast.makeText(SignUpActivity.this, "Select User Type", Toast.LENGTH_SHORT).show();
//                        cancelSignup = true;
//                    }
                    else if (cancelSignup) {
                        // error in login
                        focusView.requestFocus();
                    } else {
                        String s_day = String.valueOf(selectedday);
                        String s_month = String.valueOf(selectedmonth);
                        String s_year = String.valueOf(selectedyear);
                        dateString = s_year + "-" + s_month + "-" + s_day;

                     //   Toast.makeText(SignUpActivity.this, "Date is" + dateString, Toast.LENGTH_LONG).show();


                        Log.e("date format", dateString);

                        signup();
                    }
                }
        });
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
        txt_title.setText("Sign Up");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void datemonth() {
        Calendar now = Calendar.getInstance();

        for (int i = 1; i <= 12; i++) {
            allmonths.add(Integer.toString(i));
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, allmonths);
        month.setPrompt("Select Month");
        month.setAdapter(adapter);

        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedmonth = Integer.parseInt(month.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        cyear = now.get(Calendar.YEAR);
        int minage = cyear - 15;
        int maxage = cyear - 65;
        for (int i = minage; i >= maxage; i--) {
            allyears.add(Integer.toString(i));
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, allyears);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearAdapter);

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedyear = Integer.parseInt(year.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        for (int x = 1; x <= 31; x++) {
            alldays.add(Integer.toString(x));
        }

        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, alldays);
        day.setAdapter(dayAdapter);

        day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedday = Integer.parseInt(day.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void signup() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "signup_step_one");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "signup_step_one", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    ;
                   // Log.d("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(SignUpActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {
                                JSONArray jsonArray=object.getJSONArray("data");
                            Log.e("Errors ",""+jsonArray.toString());
                            if (jsonArray.length() > 0) {
                                StringBuffer buffer = new StringBuffer();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        String error = jsonArray.getString(i);
                                        buffer.append("" + error + "\n");
                                        Log.e("Error loop list", "" + error);
                                        }
                                message(buffer.toString());
                            }
                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONObject object1 = object.getJSONObject("data");

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getdata.setfirstname(user_fname);
                            getdata.setlastname(lname);
                            getdata.setemail(email);
                            getdata.setuserloginname(user);
                            getdata.setpassword(password);
                            getdata.setconfirmpassword(confirm_password);
                            getdata.setcontactnumber(phnumber);
                            getdata.setbday(dateString);
                            Intent intent = new Intent(SignUpActivity.this, AddressActivity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);

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
                        Toast.makeText(SignUpActivity.this, "Timeout Error",
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

                    params.put("user_first_name", user_fname);
                    params.put("user_last_name", lname);
                    params.put("user_login", user);
                    params.put("user_login_password", password);
                    params.put("confirm_user_login_password", confirm_password);
                    params.put("user_email", email);
                    params.put("user_primary_contact", phnumber);
                    params.put("user_dob", dateString);
//
//                    Log.e("first name", user_fname);
//                    Log.e("Last name", lname);
//                    Log.e("user login", user);
//                Log.e("user login password", password);
//                Log.e("confirm login password", confirm_password);
//                Log.e("user email", email);
//                Log.e("user phone", phnumber);
//                    Log.e("user dob", dateString);

                return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(SignUpActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void message(String msg) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customTitleView = inflater.inflate(R.layout.message, null);
        TextView msgtitle=(TextView)customTitleView.findViewById(R.id.msgtitle);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCustomTitle(customTitleView);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setMessage(""+msg);
        builder.show();
    }


}
