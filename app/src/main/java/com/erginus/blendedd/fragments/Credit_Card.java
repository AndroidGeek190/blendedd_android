package com.erginus.blendedd.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.erginus.blendedd.CategoryItemActivity;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;
import com.erginus.blendedd.R;
import com.erginus.blendedd.UserHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazer on 11/3/2015.
 */
public class Credit_Card extends Fragment {
    TextView btn_update;
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title,credit_card_text;
    LinearLayout ll_navigation;
    Spinner spr_month, spr_year;
    //ArrayList<String> list_month, list_year;
    ArrayList<String> list_year = new ArrayList<String>();
    String list_month[]={"01","02","03","04","05","06","07","08","09","10","11","12"};
    long user_id;
    String user_security_hash;
    EditText user_credit_card_name,user_credit_card_number,user_credit_card_cvv;
    String credit_card_name,credit_card_number,credit_card_cvv;
    int selected_month,selected_year,currentmonth,currentyear;
    SharedPreference getdata;
    public static SharedPreferences preferences;
    public Credit_Card(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.updatecreditcard, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Calendar now=Calendar.getInstance();
        currentmonth=now.get(Calendar.MONTH);
        getdata=new SharedPreference(getActivity());
        user_id= Long.parseLong(getdata.getuserid());
        user_security_hash=getdata.gethashcode();
        credit_card_number=getdata.getcreditcardnumber();

        //Log.e("Credit card updated ",credit_card_number );

        user_credit_card_name=(EditText)rootView.findViewById(R.id.editText_name);
        user_credit_card_number=(EditText)rootView.findViewById(R.id.editText_card_number);
        user_credit_card_cvv=(EditText)rootView.findViewById(R.id.editText_cvv);
        credit_card_text=(TextView)rootView.findViewById(R.id.credit_card_text);
        btn_update=(TextView)rootView.findViewById(R.id.button_update);
        credit_card_text.setText(credit_card_number);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                credit_card_name = user_credit_card_name.getText().toString();
                credit_card_number = user_credit_card_number.getText().toString();
                credit_card_cvv = user_credit_card_cvv.getText().toString();

                boolean creditcard = false;
                View focusView = null;

                if (TextUtils.isEmpty(credit_card_name)) {
                    user_credit_card_name.setError("Fill Credit Card name");
                    focusView = user_credit_card_name;
                    creditcard = true;
                }
                if (TextUtils.isEmpty(credit_card_number)) {
                    user_credit_card_number.setError("Fill Credit Card Number");
                    focusView = user_credit_card_number;
                    creditcard = true;
                }
                if (TextUtils.isEmpty(credit_card_cvv)) {
                    user_credit_card_cvv.setError("Fill Credit Card CVV Number");
                    focusView = user_credit_card_cvv;
                    creditcard = true;
                }


                if (creditcard) {
                    // error in login
                    focusView.requestFocus();
                } else {
                    preferences  = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("user_credit_card_name");
                    editor.remove("user_credit_card_number");
                    editor.remove("user_credit_card_expiry_month");
                    editor.remove("user_credit_card_expiry_year");
                    editor.remove("user_credit_card_cvv");
                    editor.commit();
                    String number = credit_card_number;
                    char[] indexnumber = number.toCharArray();
                    for(int i=0;i<=indexnumber.length-5;i++)
                    {
                        indexnumber[i] = 'X';
                    }
                    number = String.valueOf(indexnumber);

                    getdata.setcreditcardnumber(number);
                    if((currentmonth>=selected_month)&&(currentyear==selected_year))
                    {

                        Toast.makeText(getActivity(),"Please Check Your Expiry Month",Toast.LENGTH_LONG).show();
                    }
                    else {
                        update_information();
                    }
                    Log.e("Month "," "+currentmonth+" "+selected_month+" "+currentyear+" "+selected_year);
                }
            }

        });
        spr_month=(Spinner)rootView.findViewById(R.id.spinner_mnth);
        spr_year=(Spinner)rootView.findViewById(R.id.spinner_year);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_month);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spr_month.setAdapter(adapter);
        spr_month.setPrompt("Select Month");
        spr_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_month = Integer.parseInt(spr_month.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        currentyear=now.get(Calendar.YEAR);
        int maxyear=currentyear+7;

        for (int i = currentyear; i <= maxyear; i++) {
            list_year.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list_year);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spr_year.setAdapter(adapter2);
        spr_year.setPrompt("Select Year");
        spr_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_year= Integer.parseInt(spr_year.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }
    public void update_information()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "update_credit_card");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "update_credit_card", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                  //  Log.e("", ".......response====" + response.toString());

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
                                    JSONArray jsonArray = object.getJSONArray("data");

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if(!getdata.getproductid().equalsIgnoreCase("")) {
                                Intent intent = new Intent(getActivity(), CategoryItemActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Intent intent = new Intent(getActivity(), UserHomeActivity.class);
                                startActivity(intent);
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
                    params.put("user_id",""+user_id);
                    params.put("user_security_hash",""+user_security_hash);
                    params.put("user_credit_card_name",credit_card_name);
                    params.put("user_credit_card_number",credit_card_number);
                    params.put("user_credit_card_expiry_month",""+selected_month);
                    params.put("user_credit_card_expiry_year",""+selected_year);
                    params.put("user_credit_card_cvv", credit_card_cvv);

                    Log.e("user id",""+user_id);
                    Log.e("user security hash",""+user_security_hash);
                    Log.e("user_credit_card_name", credit_card_name);
                    Log.e("user_credit_card_number", credit_card_number);
                    Log.e("expiry_month", "" + selected_month);
                    Log.e("card_expiry_year", "" + selected_year);
                    Log.e("user_credit_card_cvv", "" + credit_card_cvv);

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

    public SharedPreference setPreferences(String cardname,String cardnumber,int month,int year,String cvv)
    {
        SharedPreference data=new SharedPreference(getActivity());
        data.setcardname(cardname);
        data.setcreditcardnumber(cardnumber);
        data.setcardexpirymonth(month);
        data.setcardexpiryyear(year);
        data.setcardcvv(cvv);

        return data;
    }
}