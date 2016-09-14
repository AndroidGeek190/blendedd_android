package com.erginus.blendedd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import com.erginus.blendedd.Drawer_Effect.MenuFragment;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//import com.mxn.soul.flowingdrawer_core.MenuFragment;
//import com.squareup.picasso.Picasso;

/**
 * Created by nazer on 3/29/2016.
 */
public class MyMenuFragment extends MenuFragment {
    String sign_up_info, about_us, how_does_it_work, faq, contact_us, post_id,stories, feedback, privacy, terms, cat_title;
    private ImageView ivMenuUserProfilePhoto;
    NavigationView navigationView;
    HomeActivity obj;
    SharedPreference getdata;
    Menu drawerMenu;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container,
                false);

        getdata=new SharedPreference(getActivity());
        navigationView = (NavigationView)view.findViewById(R.id.vNavigation);
        drawerMenu = navigationView.getMenu();
//        if(getdata.getuserid().equalsIgnoreCase("null")) {
//            Log.e("userid",""+getdata.getuserid());
//            drawerMenu.findItem(R.id.User_Activities).setVisible(false);
//            drawerMenu.findItem(R.id.User_Settings).setVisible(false);
//            drawerMenu.findItem(R.id.Logout).setVisible(false);
//        }
//        else
//        {
//            Log.e("userid else",""+getdata.getuserid());
//            drawerMenu.findItem(R.id.User_Activities).setVisible(true);
//            drawerMenu.findItem(R.id.User_Settings).setVisible(true);
//            drawerMenu.findItem(R.id.Logout).setVisible(true);
//        }

        GetStrings();


navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        if (menuItem.isChecked()) menuItem.setChecked(false);
        else menuItem.setChecked(true);
        switch (menuItem.getItemId()) {

//            case R.id.User_Activities:
//                if(getdata.getdrawer().equalsIgnoreCase("0"))
//                {
//                    HomeActivity.mLeftDrawerLayout.closeDrawer();
//                }
//                if(getdata.getdrawer().equalsIgnoreCase("1"))
//                {
//                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
//                }
//                Intent intent12 = new Intent(getActivity(), UserActivity.class);
//                startActivity(intent12);
//            return true;

            case R.id.User_Settings:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent13 = new Intent(getActivity(), User_Settings.class);
                startActivity(intent13);
                return true;

            case R.id.About:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
               // HomeActivity.mLeftDrawerLayout.closeDrawer();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Thank You for using Blendedd's App. This app is only for renting items and paying for services. for full features please visit our website - www.blendedd.com");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //if user pressed "yes", then he is allowed to exit from application
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            case R.id.About_Us:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("url", about_us);
                intent.putExtra("title", "About Us");


                startActivity(intent);
                return true;

            case R.id.work:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent2 = new Intent(getActivity(), WebViewActivity.class);
                intent2.putExtra("url", how_does_it_work);
                intent2.putExtra("title", "How Does It Work");
               // HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent2);
                return true;
            case R.id.FAQ:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent3 = new Intent(getActivity(), WebViewActivity.class);
                intent3.putExtra("url", faq);
                intent3.putExtra("title", "FAQ");
               // HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent3);
                return true;
            case R.id.Contact_Us:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent4 = new Intent(getActivity(), WebViewActivity.class);
                intent4.putExtra("url", contact_us);
                intent4.putExtra("title", "Contact Us");
               // HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent4);
                return true;
            case R.id.Logout:
                getdata.cleardata();
                LoginManager.getInstance().logOut();
                Intent intent15 = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent15);
                return true;
            case R.id.Feedback:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent6 = new Intent(getActivity(), WebViewActivity.class);
                intent6.putExtra("url", feedback);
                intent6.putExtra("title", "Feedback");
                //HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent6);
                return true;
            case R.id.Privacy:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent7 = new Intent(getActivity(), WebViewActivity.class);
                intent7.putExtra("url", privacy);
                intent7.putExtra("title", "Privacy");
//                HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent7);
                return true;
            case R.id.Terms:
                if(getdata.getdrawer().equalsIgnoreCase("0"))
                {
                    HomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                if(getdata.getdrawer().equalsIgnoreCase("1"))
                {
                    UserHomeActivity.mLeftDrawerLayout.closeDrawer();
                }
                Intent intent8 = new Intent(getActivity(), WebViewActivity.class);
                intent8.putExtra("url", terms);
                intent8.putExtra("title", "Terms");
               // HomeActivity.mLeftDrawerLayout.closeDrawer();
                startActivity(intent8);

                return true;
            default:
                Toast.makeText(getActivity(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                return true;

        }
    }
});
//        ivMenuUserProfilePhoto = (ImageView) view.findViewById(R.id.ivMenuUserProfilePhoto);
//        setupHeader();
        return  setupReveal(view) ;
    }

//    private void setupHeader() {
//        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size);
//        String profilePhoto = getResources().getString(R.string.user_profile_photo);
//        Picasso.with(getActivity())
//                .load(profilePhoto)
//                .placeholder(R.drawable.img_circle_placeholder)
//                .resize(avatarSize, avatarSize)
//                .centerCrop()
//                .transform(new CircleTransformation())
//                .into(ivMenuUserProfilePhoto);
//    }
public void GetStrings() {
    try {
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        Log.e("", "Strings" + MapAppConstant.API + "/strings");
        StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "/strings", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(response);
                    String serverCode = object.getString("code");
                    String serverMessage = object.getString("message");
                    //  Toast.makeText(HomeActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                    if (serverCode.equalsIgnoreCase("0")) {
                        Toast.makeText(getActivity(), "Server error", Toast.LENGTH_SHORT).show();
                    }
                    if (serverCode.equalsIgnoreCase("1")) {
                        try {

                            if ("1".equals(serverCode)) {
                                JSONObject object1 = object.getJSONObject("data");
                                about_us = object1.getString("about_us");
                                sign_up_info = object1.getString("signup_info");
                                how_does_it_work = object1.getString("how_does_it_work");
                                faq = object1.getString("faq");
                                contact_us = object1.getString("contact_us");
                                stories = object1.getString("stories");
                                feedback = object1.getString("feedback");
                                privacy = object1.getString("privacy");
                                terms = object1.getString("terms");
                                Log.d("mcrtbkeurrrrrrrrrr", about_us + " " + sign_up_info + " " + how_does_it_work);
                            }
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
//    public void onOpenMenu(){
//        Toast.makeText(getActivity(), "onOpenMenu", Toast.LENGTH_SHORT).show();
//    }
//    public void onCloseMenu(){
//        Toast.makeText(getActivity(),"onCloseMenu",Toast.LENGTH_SHORT).show();
//    }
}
