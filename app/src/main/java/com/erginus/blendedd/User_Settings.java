package com.erginus.blendedd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.User_Tabs;
import com.erginus.blendedd.R;

/**
 * Created by nazer on 11/3/2015.
 */
public class User_Settings extends AppCompatActivity {
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
    Toolbar toolbar;
    ImageView img_back;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreference getdata;
    public static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings);
        getdata=new SharedPreference(User_Settings.this);

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
        txt_title.setText("User Settings");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!getdata.getproductid().equalsIgnoreCase(""))
                {

                    preferences  = PreferenceManager.getDefaultSharedPreferences(User_Settings.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("product_id");
                    editor.commit();
                    finish();
                }
                else {
                    finish();
                }
            }
        });



        mFragmentManager = getSupportFragmentManager();

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new User_Tabs()).commit();

    }
}
