package com.erginus.blendedd.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.erginus.blendedd.ForgotPasswordActivity;
import com.erginus.blendedd.HomeActivity;
import com.erginus.blendedd.R;
import com.erginus.blendedd.SignUpActivity;
import com.erginus.blendedd.UserHomeActivity;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment {

    EditText edt_email, edt_password;
    ImageView img_login, img_register;
    TextView textView_forgot;
    String email, password, user_id,user_name,firstname,lastname, birthday;
    String userid;
    String user_status,hashcode,user_fname,user_lname,user_email,user_address_line_1,user_address_line_2,user_zipcode,credit_card_number;

    private CallbackManager callbackManager;
    //Prefshelper prefshelper;
    SharedPreference getdata;
    // private TextView textView,idno,mail;
    LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        //prefshelper=new Prefshelper(getActivity());
        getdata=new SharedPreference(getActivity());
        callbackManager = CallbackManager.Factory.create();

        StrictMode.ThreadPolicy policy =
                new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginButton = (LoginButton)rootView.findViewById(R.id.login_button);
        loginButton.setBackgroundResource(R.drawable.fb);
        loginButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        loginButton.setReadPermissions("public_profile", "email", "user_birthday");
        // If using in a fragment
        loginButton.setFragment(this);
        // Other app specific specialization
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                profileTracker = new ProfileTracker() {
                    @Override
                    protected void onCurrentProfileChanged( Profile oldProfile,
                                                            Profile currentProfile) {
                    }};

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {// Application code

                                Log.e(" First LoginActivity", response.toString());
                                if (response.getError() != null) {
                                    // handle error
                                } else {

                                    try {
                                        email = object.optString("email");
                                        user_id = object.getString("id");
                                        //user_name=object.getString("name");
                                        birthday=object.getString("birthday");
                                        //  int firstSpace = user_name.indexOf(" ");
                                        //fname = user_name.substring(0, firstSpace);
                                        //lname = user_name.substring(firstSpace).trim();

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Log.e(" first email facebook", email + " " + fname + " " + lname + " " + birthday);
//                                    Intent intent = new Intent(getActivity(), UserHomeActivity.class);
//                                    startActivity(intent);
//                                    getActivity().overridePendingTransition(0, 0);
                                    //Log.e("email facebook", email+" "+fname+" "+lname+" " + birthday);

                                }

                            }

                        }

                );
                Bundle parameters = new Bundle();
                parameters.putString("fields", "email, name, id, birthday");
                request.setParameters(parameters);
                request.executeAsync();
                profileTracker.startTracking();
            }

            @Override
            public void onCancel() {
                // App code
                Log.e("facebook - onCancel", "cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e("facebook - onError", exception.getMessage());
            }
        });
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken accessToken, AccessToken accessToken2) {
                System.out.println("acesstoken trackercalled");
            }
        };
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        final Profile profile = Profile.getCurrentProfile();
                        if (profile != null) {
                            user_id = profile.getId();
                            Log.e("user name", profile.getFirstName() + " " + profile.getLastName());
//                            fname=profile.getFirstName();
//                            lname=profile.getLastName();
//                            getdata.setfirstname(profile.getFirstName());
//                            getdata.setlastname(profile.getLastName());
                        }

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        // Application code
                                        Log.e("LoginActivity", response.toString());
                                        if (response.getError() != null) {
                                            // handle error
                                        } else {

                                            try {
                                                email = object.optString("email");
                                                user_id = object.getString("id");
                                                user_name=object.getString("name");
                                                birthday=object.getString("birthday");

//                                                int firstSpace = user_name.indexOf(" ");
//
//                                                firstname = user_name.substring(0, firstSpace);
//                                                lastname = user_name.substring(firstSpace).trim();

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            Log.e("facebook email ", email + " " + user_name + " " + firstname + " " + lastname + " " + birthday);
                                            namesplit(user_name);


                                        }

                                    }

                                }

                        );
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "email,name, id, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        accessTokenTracker.startTracking();
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException e) {

                    }

                });


        edt_email=(EditText)rootView.findViewById(R.id.editText_email);
        edt_password=(EditText)rootView.findViewById(R.id.editText_password);
        img_login=(ImageView)rootView.findViewById(R.id.image_login);
        img_register=(ImageView)rootView.findViewById(R.id.image_register);
        textView_forgot=(TextView)rootView.findViewById(R.id.textView_forgot);

        img_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                password = edt_password.getText().toString();
                boolean cancelLogin = false;
                View focusView = null;

                if (TextUtils.isEmpty(email)) {
                    edt_email.setError(getString(R.string.email_required));
                    focusView = edt_email;
                    cancelLogin = true;
                }
                if (TextUtils.isEmpty(password)) {
                    edt_password.setError(getString(R.string.pswrd_required));
                    focusView = edt_password;
                    cancelLogin = true;
                }
                if (cancelLogin) {
                    // error in login
                    focusView.requestFocus();
                } else {

//                    Intent intent=new Intent(getActivity(), UserHomeActivity.class);
//
//                    startActivity(intent);
                    Login();

                }
            }
        });


        textView_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        img_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(0, 0);
            }
        });
        return  rootView;
    }

    public void namesplit(String name)
    {
        String[] fullname = name.split(" ");
        firstname=fullname[0];
        lastname=fullname[1];
        Log.e("first name",firstname);
        Log.e("last name",lastname);
        startfblogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
    public void startfblogin()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

           // Log.e("", "Strings" + MapAppConstant.API + "social_media_login");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"social_media_login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
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
                                    hashcode=object1.getString("user_security_hash");
                                    userid= object1.getString("user_id");
                                    user_fname=object1.getString("user_first_name");
                                    user_lname=object1.getString("user_last_name");
                                    user_email=object1.getString("user_email");
                                    user_status=object1.getString("user_status");
                                    user_address_line_1=object1.getString("user_address_line_1");
                                    user_address_line_2=object1.getString("user_address_line_2");
                                    user_zipcode=object1.getString("user_zipcode");
                                    credit_card_number=object1.getString("user_credit_card_number");

                                    setPref(userid, hashcode, user_fname, user_lname, user_email, user_status, user_address_line_1, user_address_line_2, user_zipcode, credit_card_number);
                                }

                            }

                            catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent=new Intent(getActivity(), UserHomeActivity.class);
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

                    params.put("user_facebook_id", user_id);
                    params.put("user_first_name", firstname);
                    params.put("user_last_name", lastname);
                    params.put("user_email", email);

                    Log.e("user_facebook_id", user_id);
                    Log.e("user_first_name",firstname);
                    Log.e("user_last_name",lastname);
                    Log.e("user_email",email);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SharedPreference setPref(String userid, String hashcode, String fname,String lname,String email,String userstatus,String addrone,String addrtwo,String zip,String cnumber)
    {
        SharedPreference data=new SharedPreference(getActivity());
        data.setuserid(userid);
        data.sethashcode(hashcode);
        data.setfirstname(fname);
        data.setlastname(lname);
        data.setemail(email);
        data.setuserstatus(userstatus);
        data.setaddressone(addrone);
        data.setaddresstwo(addrtwo);
        data.setzipcode(zip);
        data.setcreditcardnumber(cnumber);
        data.setloginwith("fb");
        return data;
    }
    public void Login()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "login");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.e("response",""+response);
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

                                    hashcode=object1.getString("user_security_hash");
                                    userid= object1.getString("user_id");
                                    user_fname=object1.getString("user_first_name");
                                    user_lname=object1.getString("user_last_name");
                                    user_email=object1.getString("user_email");
                                    user_address_line_1=object1.getString("user_address_line_1");
                                    user_address_line_2=object1.getString("user_address_line_2");
//                                     user_Country=object1.getInt("countries_id");
//                                     user_state=object1.getInt("state_id");
//                                     user_city=object1.getInt("city_id");
                                    user_zipcode=object1.getString("user_zipcode");
                                    credit_card_number=object1.getString("user_credit_card_number");
                                    user_status=object1.getString("user_status");

                                    setPreferences(userid, hashcode, user_fname, user_lname, user_email, user_address_line_1, user_address_line_2, user_zipcode, credit_card_number,user_status);
                                }

                            }

                            catch (Exception e) {
                                e.printStackTrace();
                            }

                            Intent intent=new Intent(getActivity(), UserHomeActivity.class);

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

                    params.put("user_login", email);
                    params.put("user_login_password", password);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public SharedPreference setPreferences(String userid, String hashcode, String fname,String lname,String email,String addressone,String addresstwo,String zipcode,String creditcard,String status)
    {
        SharedPreference data=new SharedPreference(getActivity());
        data.setuserid(userid);
        data.sethashcode(hashcode);
        data.setfirstname(fname);
        data.setlastname(lname);
        data.setemail(email);
        data.setaddressone(addressone);
        data.setaddresstwo(addresstwo);
        data.setzipcode(zipcode);
        data.setcreditcardnumber(creditcard);
        data.setuserstatus(status);
        return data;
    }



}
