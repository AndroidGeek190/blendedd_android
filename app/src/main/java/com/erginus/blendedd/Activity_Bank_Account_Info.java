package com.erginus.blendedd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
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

/**
 * Created by nazer on 2/6/2016.
 */
public class Activity_Bank_Account_Info  extends AppCompatActivity {
    TextView btn_continue;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;

    long user_id;
    String user_security_hash;
    EditText user_credit_card_name,user_credit_card_number,user_credit_card_cvv;
    String Bank_name, Bank_number, Bank_route;
CheckBox box;
    int check=0;
    SharedPreference getdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bank_account_info);

        getdata=new SharedPreference(Activity_Bank_Account_Info.this);
//        user_id= Long.parseLong(getdata.getuserid());
//        user_security_hash=getdata.gethashcode();


        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                        | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        user_credit_card_name=(EditText)findViewById(R.id.editText_name);
        user_credit_card_number=(EditText)findViewById(R.id.editText_card_number);
        user_credit_card_cvv=(EditText)findViewById(R.id.editText_cvv);
        box=(CheckBox)findViewById(R.id.checkBox_1);
box.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(box.isChecked())
        {
            check=1;
        }
        else
        {
            check=0;
        }
    }
});
        btn_continue=(TextView)findViewById(R.id.button_continue);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bank_name = user_credit_card_name.getText().toString();
                Bank_number = user_credit_card_number.getText().toString();
                Bank_route = user_credit_card_cvv.getText().toString();

                boolean creditcard = false;
                View focusView = null;

                if (TextUtils.isEmpty(Bank_name)) {
                    user_credit_card_name.setError("BankName is required.");
                    focusView = user_credit_card_name;
                    creditcard = true;
                }
                if (TextUtils.isEmpty(Bank_number)) {
                    user_credit_card_number.setError(" BankAccount Number is required.");
                    focusView = user_credit_card_number;
                    creditcard = true;
                }
                if (TextUtils.isEmpty(Bank_route)) {
                    user_credit_card_cvv.setError("Bank Route Code is required.");
                    focusView = user_credit_card_cvv;
                    creditcard = true;
                }
                if (check==0) {
                    Toast.makeText(Activity_Bank_Account_Info.this, "Please Accepts Online Payments", Toast.LENGTH_SHORT).show();
                    creditcard = true;
                }

                if (creditcard) {
                    // error in login
                    focusView.requestFocus();
                }
                    else {
                        get_credit_card_information();
                    }


            }

        });



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
        txt_title.setText("BankAccount Info");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void get_credit_card_information()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(Activity_Bank_Account_Info.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "signup_step_three");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "signup_step_three", new Response.Listener<String>() {
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
                        Toast.makeText(Activity_Bank_Account_Info.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {
                            JSONArray jsonArray = object.getJSONArray("data");
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
                                    JSONArray jsonArray = object.getJSONArray("data");




                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getdata.setBankname(Bank_name);
                            getdata.setBank_Account_number(Bank_number);
                            getdata.setBank_route_number(Bank_route);
                            Intent intent = new Intent(Activity_Bank_Account_Info.this, CommunicationPrefrencesActivity.class);
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
                        Toast.makeText(Activity_Bank_Account_Info.this, "Timeout Error",
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
//                params.put("user_id",""+user_id);
//                params.put("user_security_hash",""+user_security_hash);
                    params.put("user_credit_card_name", Bank_name);
                    params.put("user_credit_card_number", Bank_number);

                    params.put("user_credit_card_cvv", Bank_route);

//                Log.e("user id",""+user_id);
//                Log.e("user security hash", user_security_hash);
                    Log.e("user_credit_card_name", Bank_name);
                    Log.e("user_credit_card_number", Bank_number);
                    Log.e("user_credit_card_cvv", "" + Bank_route);

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(Activity_Bank_Account_Info.this.getApplicationContext()).addToRequestQueue(sr);


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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        onResume();
    }
}
