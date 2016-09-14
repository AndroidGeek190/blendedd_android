package com.erginus.blendedd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by nazer on 2/8/2016.
 */
public class Add_Post_Rates extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
    EditText rate1,rate2,rate3,rate4;
    TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_rates);

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
        txt_title.setText("Post Rates");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rate1=(EditText)findViewById(R.id.edit_post_rate1);
        rate2=(EditText)findViewById(R.id.edit_post_rate2);
        rate3=(EditText)findViewById(R.id.edit_post_rate3);
        rate4=(EditText)findViewById(R.id.edit_post_rate4);
        submit=(TextView)findViewById(R.id.button_continue);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_Post_Rates.this, Add_Post_Availability.class);
                startActivity(intent);
            }
        });
    }



}
