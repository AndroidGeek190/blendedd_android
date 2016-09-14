package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import com.erginus.blendedd.Commons.Prefshelper;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazer on 11/6/2015.
 */
public class CheckoutActivity extends AppCompatActivity {
String title,item_price,button_display_price,postid,buttonid,user_id,user_hashcode,card_number;
    Prefshelper prefshelper;
    TextView post_title,price,cardnumber;
    TextView payment;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        Intent intent=getIntent();
        item_price=intent.getStringExtra("button_price");
        button_display_price=intent.getStringExtra("button_display_price");
        postid=intent.getStringExtra("postid");
        buttonid=intent.getStringExtra("button_id");
        title=intent.getStringExtra("title");

        prefshelper=new Prefshelper(CheckoutActivity.this);
        user_id=prefshelper.getUserIdFromPreference();
        user_hashcode=prefshelper.getUserSecHashFromPreference();
        card_number=prefshelper.getCreditCard();
        post_title=(TextView)findViewById(R.id.title_post_top);
        price=(TextView)findViewById(R.id.text_price);
        cardnumber=(TextView)findViewById(R.id.edit_cardnumber);
        payment=(TextView)findViewById(R.id.button_continue);

        post_title.setText(title);
        price.setText(button_display_price);
        cardnumber.setText(card_number);

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
        txt_title.setText("Checkout");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_payment();
            }
        });

    }
    public void user_payment() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(CheckoutActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "purchase");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "purchase", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.e("", ".......response====" + response.toString());

                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(CheckoutActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {
                            Toast.makeText(CheckoutActivity.this, "Check Your Credit Card", Toast.LENGTH_SHORT).show();
                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONObject object1 = object.getJSONObject("data");
                                    Toast.makeText(CheckoutActivity.this, "payment successful", Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(CheckoutActivity.this, UserHomeActivity.class);
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
                        Toast.makeText(CheckoutActivity.this, "Timeout Error",
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

                    params.put("user_id", user_id);
                    params.put("user_security_hash", user_hashcode);
                    params.put("amount", item_price);
                    params.put("posts_id", postid);
                    params.put("pricing_options_id", buttonid);

                    Log.e("user_id ", user_id);
                    Log.e("user_security_hash ", user_hashcode);
                    Log.e("item Price ", item_price);
                    Log.e("button display price ", button_display_price);
                    Log.e("post id ", postid);
                    Log.e("button id", buttonid);
                    Log.e("title", title);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(CheckoutActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
