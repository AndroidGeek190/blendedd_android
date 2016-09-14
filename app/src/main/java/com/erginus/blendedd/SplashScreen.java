package com.erginus.blendedd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.erginus.blendedd.Commons.ConnectionDetector;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;
import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class
        SplashScreen extends Activity {
    private static int SPLASH_TIME_OUT = 6500;
    Animation translateAnim;
    SharedPreference getdata;
    String status, user_status,userid,userhashcode;
    Boolean isInternetPresent = false;
    private SimpleDraweeView mAnimatedGifView;
    // Connection detector class
    ConnectionDetector cd;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FLog.setMinimumLoggingLevel(FLog.VERBOSE);
        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setRequestListeners(listeners)
                .setBitmapsConfig(Bitmap.Config.ARGB_8888)
                .build();
        Fresco.initialize(this, config);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

//        translateAnim = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.bottom_up);
//         imageView = (ImageView) findViewById(R.id.imageView);
        mAnimatedGifView = (SimpleDraweeView) findViewById(R.id.animated_gif);
        // creating connection detector class instance

        DraweeController animatedGifController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(Uri.parse("res:///" + R.drawable.logo_blendedd_1))
                .build();
        mAnimatedGifView.setController(animatedGifController);

       internet();


    }
public void internet()
{
    cd = new ConnectionDetector(getApplicationContext());
    isInternetPresent = cd.isConnectingToInternet();

    // check for Internet status
    if (isInternetPresent) {
        // Internet Connection is Present
        // make HTTP requests
//            showAlertDialog(SplashScreen.this, "Internet Connection",
//                    "You have internet connection", true);

        getdata = new SharedPreference(SplashScreen.this);
        userid=getdata.getuserid();
        Log.e("user id saplash",""+userid);
        userhashcode=getdata.gethashcode();
        status = getdata.getuserstatus();
        Log.e("user id status saplash",""+status);
//        userid=getdata.getuserid();
//        userhashcode=getdata.gethashcode();


//        Thread t=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(6500);
//                    if (status.equalsIgnoreCase("1")) {
//                        logintohome();
//
//                    } else {
//                        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
//                        startActivity(intent);
//                        overridePendingTransition(0, 0);
//                        finish();
//
//
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });t.start();

       // imageView.startAnimation(translateAnim);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                    if (status.equalsIgnoreCase("1")) {
                        logintohome();

                    } else {
                        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();


                    }


            }
        }, SPLASH_TIME_OUT);

    } else {
        // Internet connection is not present
        // Ask user to connect to Internet
        showAlertDialog(SplashScreen.this, "No Internet Connection",
                "You don't have internet connection.", false);
    }

}
    public void logintohome() {
        try {
            final ProgressDialog pDialog = new ProgressDialog(SplashScreen.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "session_login");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "session_login", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        Toast.makeText(SplashScreen.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {


                                if ("1".equals(serverCode)) {
                                    JSONObject object1 = object.getJSONObject("data");
                                    user_status = object1.getString("user_status");

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (user_status.equalsIgnoreCase(status)) {
                                Intent intent = new Intent(SplashScreen.this, UserHomeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            } else {
                                Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
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
                        Toast.makeText(SplashScreen.this, "Timeout Error",
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

                    params.put("user_id", userid);
                    params.put("user_security_hash", userhashcode);
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(SplashScreen.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

       alertDialog.setIcon((status) ? R.drawable.connectionsuccess : R.drawable.connectionerror);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);//
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to exit?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                dialog.cancel();
                internet();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });



        AlertDialog alert=builder.create();
        alert.show();
    }
}
