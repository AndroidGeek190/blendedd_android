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
import android.widget.EditText;
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
import com.erginus.blendedd.R;
import com.erginus.blendedd.UserHomeActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nazer on 11/3/2015.
 */
public class User_Change_Password extends Fragment {
    EditText edit_email, edit_password, edit_confirm_password;
    TextView bt_update;
    String user_security_hash;
    long user_id;
    String password,comfirm_password,newhashcode;
    SharedPreferences preferences;
    SharedPreference getdata;
    public User_Change_Password() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.user_change_password, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        getdata=new SharedPreference(getActivity());
        user_id= Long.parseLong(getdata.getuserid());
        user_security_hash=getdata.gethashcode();

        edit_password = (EditText) rootView.findViewById(R.id.editText_password);
        edit_confirm_password = (EditText) rootView.findViewById(R.id.editText_confirm_password);
        bt_update = (TextView) rootView.findViewById(R.id.button_update);

        bt_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password =edit_password.getText().toString();
                comfirm_password =edit_confirm_password.getText().toString();


                boolean Update = false;
                View focusView = null;
                if (TextUtils.isEmpty(password)) {
                    edit_password.setError("Fill new Password");
                    focusView = edit_password;
                    Update = true;
                }
                if (TextUtils.isEmpty(comfirm_password)) {
                    edit_confirm_password.setError("Fill Confirm Password");
                    focusView = edit_confirm_password;
                    Update = true;
                }
                if (Update) {
                    // error in login
                    focusView.requestFocus();
                }

                else
                {
                    preferences  = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("user_security_hash");
                    editor.commit();
                    Update();
                }
            }
        });
        return rootView;
    }

    public void Update() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "change_password");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "change_password", new Response.Listener<String>() {
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
                                    user_security_hash=object1.getString("user_security_hash");
                                    setPreferences(user_security_hash);
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
                    params.put("user_login_password", password);
                    params.put("user_confirm_password", comfirm_password);

                    Log.e("user id", "" + user_id);
                    Log.e("Hash code ",user_security_hash);
                    //Log.e("user zipcode",user_zipcode);
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
    public SharedPreference setPreferences(String hashcode)
    {
        SharedPreference data=new SharedPreference(getActivity());
        data.sethashcode(hashcode);
        return data;
    }
}
