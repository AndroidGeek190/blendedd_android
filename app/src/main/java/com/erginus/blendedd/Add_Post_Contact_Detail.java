package com.erginus.blendedd;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * Created by nazer on 2/6/2016.
 */
public class Add_Post_Contact_Detail extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;

    RadioButton radioemail1,radioemail2;
    CheckBox check_text,check_phone;
    EditText edit_name,edit_contact;

    TextView submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_contact_detail);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
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
        txt_title.setText("Contact Detail");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        radioemail1=(RadioButton)findViewById(R.id.radio_mail1);
        radioemail2=(RadioButton)findViewById(R.id.radio_mail2);
        check_text=(CheckBox)findViewById(R.id.checkBox_text);
        check_phone=(CheckBox)findViewById(R.id.checkBox_phone);
        edit_name=(EditText)findViewById(R.id.edit_contact);
        edit_contact=(EditText)findViewById(R.id.edit_contact_number);
        submit=(TextView)findViewById(R.id.button_continue);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_Post_Contact_Detail.this, Add_Post_Item_Detail.class);
                startActivity(intent);
            }
        });

    }
}
