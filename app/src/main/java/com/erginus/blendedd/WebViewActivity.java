package com.erginus.blendedd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout  ll_navigation;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        LinearLayout ll=(LinearLayout)findViewById(R.id.view);



        String url= getIntent().getStringExtra("url");
        String title=getIntent().getStringExtra("title");

        webView = (WebView) findViewById(R.id.webView);
        webView.loadData(url, "text/html", "UTF-8");
        Animation mLoadAnimation1 = AnimationUtils.loadAnimation(WebViewActivity.this, R.anim.bottom_up);
        mLoadAnimation1.setDuration(2000);
        webView.startAnimation(mLoadAnimation1);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        Animation mLoadAnimation = AnimationUtils.loadAnimation(WebViewActivity.this, android.R.anim.slide_in_left);
        mLoadAnimation.setDuration(2000);
        toolbar.startAnimation(mLoadAnimation);

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
        txt_title.setText(title);
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
