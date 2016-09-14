package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {
    EditText edt_email;
    ImageView img_submit;
    Toolbar toolbar;
    ImageView img_menu, img_search, img_back;
    TextView txtVw_title, txt_title;
    LinearLayout  ll_navigation;
    String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtVw_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        img_back=(ImageView)toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        txtVw_title.setVisibility(View.GONE);
        txt_title=(TextView)toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);
        ll_navigation=(LinearLayout)toolbar.findViewById(R.id.ll_navi);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        txt_title.setText("Forgot Password");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edt_email=(EditText)findViewById(R.id.editText_email);
        img_submit=(ImageView)findViewById(R.id.button_submit);
        img_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                boolean cancelLogin = false;
                View focusView = null;

                if (TextUtils.isEmpty(email)) {
                    edt_email.setError(getString(R.string.email_required));
                    focusView = edt_email;
                    cancelLogin = true;
                }

                if (cancelLogin) {
                    // error in login
                    focusView.requestFocus();
                } else {
                    ForgotPassword();
                }
            }
        });
    }

    public void ForgotPassword()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(ForgotPasswordActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "/recover");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"/recover", new Response.Listener<String>() {
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
                        Toast.makeText(ForgotPasswordActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ForgotPasswordActivity.this, "Timeout Error",
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

                    params.put("email_address", email);

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(ForgotPasswordActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
