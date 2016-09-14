package com.erginus.blendedd;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by nazer on 2/6/2016.
 */
public class Add_Post_Availability extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;

    TextView submit;
int toi=0;
    Spinner sp_country;
    CheckBox one,two,three,four,five,six,seven,apply_to_all,select_all;
    EditText one_to_time,one_from_time,two_to_time,two_from_time,three_to_time,three_from_time,four_to_time,four_from_time,
            five_to_time,five_from_time,six_to_time,six_from_time,seven_to_time,seven_from_time;

     int hour,hr;
     int minute,mn;

    static final int TIME_DIALOG_ID = 999;



    private Button btn_set, btn_cancl;
    ListView spr_am_pm, spr_hr,spr_min;
    TextView tx_hr,tx_mn,tx_am_pm;
    String  selectedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_availability);

        sp_country=(Spinner)findViewById(R.id.spinner_country);
//        apply_to_all=(CheckBox)findViewById(R.id.checkBox_select_all);
       // select_all=(CheckBox)findViewById(R.id.checkBox_to_all);

        //apply_to_all.setOnClickListener(this);
       // select_all.setOnClickListener(this);

        one=(CheckBox)findViewById(R.id.checkBox_monday);
        one.setOnClickListener(this);
        two=(CheckBox)findViewById(R.id.checkBox_tues);
        two.setOnClickListener(this);
        three=(CheckBox)findViewById(R.id.checkBox_wed);
        three.setOnClickListener(this);
        four=(CheckBox)findViewById(R.id.checkBox_thursday);
        four.setOnClickListener(this);
        five=(CheckBox)findViewById(R.id.checkBox_friday);
        five.setOnClickListener(this);
        six=(CheckBox)findViewById(R.id.checkBox_saturday);
        six.setOnClickListener(this);
        seven=(CheckBox)findViewById(R.id.checkBox_Sunday);
        seven.setOnClickListener(this);


        one_from_time=(EditText)findViewById(R.id.from_time);
        one_from_time.setOnClickListener(this);
        one_to_time=(EditText)findViewById(R.id.to_time);
        one_to_time.setOnClickListener(this);

        two_from_time=(EditText)findViewById(R.id.from_time2);
        two_from_time.setOnClickListener(this);
        two_to_time=(EditText)findViewById(R.id.to_time2);
        two_to_time.setOnClickListener(this);

        three_from_time=(EditText)findViewById(R.id.from_time3);
        three_from_time.setOnClickListener(this);
        three_to_time=(EditText)findViewById(R.id.to_time3);
        three_to_time.setOnClickListener(this);

        four_from_time=(EditText)findViewById(R.id.from_time4);
        four_from_time.setOnClickListener(this);
        four_to_time=(EditText)findViewById(R.id.to_time4);
        four_to_time.setOnClickListener(this);

        five_from_time=(EditText)findViewById(R.id.from_time5);
        five_from_time.setOnClickListener(this);
        five_to_time=(EditText)findViewById(R.id.to_time5);
        five_to_time.setOnClickListener(this);

        six_from_time=(EditText)findViewById(R.id.from_time6);
        six_from_time.setOnClickListener(this);
        six_to_time=(EditText)findViewById(R.id.to_time6);
        six_to_time.setOnClickListener(this);

        seven_from_time=(EditText)findViewById(R.id.from_time7);
        seven_from_time.setOnClickListener(this);
        seven_to_time=(EditText)findViewById(R.id.to_time7);
        seven_to_time.setOnClickListener(this);

        one_from_time.setKeyListener(null);
        one_to_time.setKeyListener(null);
        two_from_time.setKeyListener(null);
        two_to_time.setKeyListener(null);
        three_from_time.setKeyListener(null);
        three_to_time.setKeyListener(null);



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
        txt_title.setText("Availability Details");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        submit=(TextView)findViewById(R.id.button_continue);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Add_Post_Availability.this, Add_Post_Images.class);
                startActivity(intent);
            }
        });
    }
    public void setCurrentTimeOnView() {
//    }

        TimePickerDialog tpd = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        Log.e("hour",""+""+hourOfDay+"  "+minute);
                        hr=hourOfDay;
                        mn=minute;
                        timesel();
                    }
                }, hour, minute, false);
        tpd.show();

    }
    public void timesel()
    {
        if(toi==1) {
            if(hr>=12)
            {
                one_from_time.setText(hr + ":" + mn+"PM");
            }
            else {
                one_from_time.setText(hr + ":" + mn+"AM");
            }


        }
        if(toi==11)
        {
            if(hr>=12)
            {
                one_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                one_to_time.setText(hr + ":" + mn+"AM");
            }

        }
        if(toi==2) {
            if(hr>=12)
            {
                two_from_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                two_from_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==22)
        {
            if(hr>=12)
            {
                two_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                two_to_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==3) {
            if(hr>=12)
            {
                three_from_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                three_from_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==33)
        {
            if(hr>=12)
            {
                three_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                three_to_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==4) {

            if(hr>=12)
            {
                four_from_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                four_from_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==44)
        {
            if(hr>=12)
            {
                four_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                four_to_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==5) {
            if(hr>=12)
            {
                five_from_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                five_from_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==55)
        {
            if(hr>=12)
            {
                five_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                five_to_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==6) {
            if(hr>=12)
            {
                six_from_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                six_from_time.setText(hr + ":" + mn+"AM");
            }
        }
        if(toi==66)
        {
            if(hr>=12)
            {
                six_to_time.setText(hr+ ":" +mn+"PM");
            }
            else {
                six_to_time.setText(hr + ":" + mn+"AM");
            }
        }
    }



    public void dialog3()
    {

        final Dialog dialog = new Dialog(Add_Post_Availability.this);
        dialog.setTitle("Select Time");
        dialog.setContentView(R.layout.dialog_time_picker_1);
        btn_cancl = (Button) dialog.findViewById(R.id.button_cancel);
        Date date=null;
        btn_set= (Button) dialog.findViewById(R.id.button_set);
        spr_hr=(ListView)dialog.findViewById(R.id.spinner_hour);
        spr_min=(ListView)dialog.findViewById(R.id.spinner_min);
        spr_am_pm=(ListView)dialog.findViewById(R.id.spinner_am_pm);

        tx_hr=(TextView)dialog.findViewById(R.id.text_hr);
        tx_mn=(TextView)dialog.findViewById(R.id.text_mnt);
        tx_am_pm=(TextView)dialog.findViewById(R.id.am_pm);

        final List<String> list2 = new ArrayList<String>();
        list2.add("00");
        list2.add("01");
        list2.add("02");
        list2.add("03");
        list2.add("04");
        list2.add("05");
        list2.add("06");
        list2.add("07");
        list2.add("08");
        list2.add("09");
        list2.add("10");
        list2.add("11");
        list2.add("12");
        list2.add("13");
        list2.add("14");
        list2.add("15");
        list2.add("16");
        list2.add("17");
        list2.add("18");
        list2.add("19");
        list2.add("20");
        list2.add("21");
        list2.add("22");
        list2.add("23");
        list2.add("24");
        list2.add("25");
        list2.add("26");
        list2.add("27");
        list2.add("28");
        list2.add("29");
        list2.add("30");
        list2.add("31");
        list2.add("32");
        list2.add("33");
        list2.add("34");
        list2.add("35");
        list2.add("36");
        list2.add("37");
        list2.add("38");
        list2.add("39");
        list2.add("40");
        list2.add("41");
        list2.add("42");
        list2.add("43");
        list2.add("44");
        list2.add("45");
        list2.add("46");
        list2.add("47");
        list2.add("48");
        list2.add("49");
        list2.add("50");
        list2.add("51");
        list2.add("52");
        list2.add("53");
        list2.add("54");
        list2.add("55");
        list2.add("56");
        list2.add("57");
        list2.add("58");
        list2.add("59");

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(Add_Post_Availability.this,
                R.layout.layout_dropdown, list2);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spr_min.setAdapter(dataAdapter2);

        spr_min.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tx_mn.setText(spr_min.getItemAtPosition(position).toString());

                Toast.makeText(Add_Post_Availability.this, ""+spr_min.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final List<String> list1 = new ArrayList<String>();

        list1.add("00");
        list1.add("01");
        list1.add("02");
        list1.add("03");
        list1.add("04");
        list1.add("05");
        list1.add("06");
        list1.add("07");
        list1.add("08");
        list1.add("09");
        list1.add("10");
        list1.add("11");
        list1.add("12");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(Add_Post_Availability.this,
                R.layout.layout_dropdown, list1);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spr_hr.setAdapter(dataAdapter1);

        spr_hr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tx_hr.setText(spr_hr.getItemAtPosition(position).toString());

                Toast.makeText(Add_Post_Availability.this, "" + spr_hr.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        final List<String> list = new ArrayList<String>();
        list.add("AM");
        list.add("PM");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(Add_Post_Availability.this,
                R.layout.layout_dropdown, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        spr_am_pm.setAdapter(dataAdapter);

        spr_am_pm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tx_am_pm.setText(spr_am_pm.getItemAtPosition(position).toString());

                Toast.makeText(Add_Post_Availability.this, "" + spr_am_pm.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                selectedTime = tx_hr + ":" + tx_mn + ":" +tx_am_pm;
                Toast.makeText(Add_Post_Availability.this, ""+selectedTime, Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.from_time:

                    setCurrentTimeOnView();
                    toi = 1;

                        break;
            case R.id.to_time:

                setCurrentTimeOnView();
                toi=11;
                break;

            case R.id.from_time2:
                if(two.isChecked()) {
                    two_from_time.setEnabled(true);
                setCurrentTimeOnView();
                toi=2;}
                break;
            case R.id.to_time2:

                setCurrentTimeOnView();
                toi=22;
                break;
            case R.id.from_time3:
                setCurrentTimeOnView();
                toi=3;
                break;
            case R.id.to_time3:
                setCurrentTimeOnView();
                toi=33;
                break;

            case R.id.from_time4:
                setCurrentTimeOnView();
                toi=4;
                break;
            case R.id.to_time4:
                setCurrentTimeOnView();
                toi=44;
                break;
            case R.id.from_time5:
                setCurrentTimeOnView();
                toi=5;
                break;
            case R.id.to_time5:
                setCurrentTimeOnView();
                toi=55;
                break;
            case R.id.from_time6:
                setCurrentTimeOnView();
                toi=6;
                break;
            case R.id.to_time6:
                setCurrentTimeOnView();
                toi=66;
                break;


//            case R.id.checkBox_select_all:
//                if(apply_to_all.isChecked()) {
//                    two.setChecked(true);
//                    three.setChecked(true);
//                    four.setChecked(true);
//                    five.setChecked(true);
//                    six.setChecked(true);
//                    seven.setChecked(true);
//                }
//                else {
//                    two.setChecked(false);
//                    three.setChecked(false);
//                    four.setChecked(false);
//                    five.setChecked(false);
//                    six.setChecked(false);
//                    seven.setChecked(false);
//                }
//            case R.id.checkBox_to_all:
//if(select_all.isChecked())
//{
//    String a=one_from_time.getText().toString();
//    String b=one_to_time.getText().toString();
//    if((a.equals(""))||(b.equals("")))
//    {
//        Toast.makeText(Add_Post_Availability.this, "Please fill first Timing", Toast.LENGTH_SHORT).show();
//    }
////    else if(
////            (!two.isChecked())&&(!three.isChecked())&&(!four.isChecked())&&(!five.isChecked())&&(!six.isChecked())
////&&(!seven.isChecked())
////            )
////    {
////        Toast.makeText(Add_Post_Availability.this, "22 G kuj ta select karlo", Toast.LENGTH_SHORT).show();
////
////    }
//    else {
//
//        if (select_all.isChecked()&&two.isChecked()) {
//                two_from_time.setText(one_from_time.getText());
//                two_to_time.setText(one_to_time.getText());
//        }
//        else
////       if(!two.isChecked())
//        {
//            two_from_time.setText("From time");
//            two_to_time.setText("To time");
//        }
//        if (select_all.isChecked()&&three.isChecked()) {
//            three_from_time.setText(one_from_time.getText());
//            three_to_time.setText(one_to_time.getText());
//        }
//        else
////        if(!three.isChecked())
//        {
//            three_from_time.setText("From time");
//            three_to_time.setText("To time");
//        }
//
//        if (four.isChecked()) {
//            four_from_time.setText(one_from_time.getText());
//            four_to_time.setText(one_to_time.getText());
//        }
//        else
////        if(!four.isChecked())
//        {
//            four_from_time.setText("From time");
//            four_to_time.setText("To time");
//        }
//        if (five.isChecked()) {
//            five_from_time.setText(one_from_time.getText());
//            five_to_time.setText(one_to_time.getText());
//        }
////        if(!five.isChecked())
//        else
//        {
//            five_from_time.setText("From time");
//            five_to_time.setText("To time");
//        }
//        if (six.isChecked()) {
//            six_from_time.setText(one_from_time.getText());
//            six_to_time.setText(one_to_time.getText());
//        }
////        if(!six.isChecked())
//        else
//        {
//            six_from_time.setText("From time");
//            six_to_time.setText("To time");
//        }
//        if (seven.isChecked()) {
//            seven_from_time.setText(one_from_time.getText());
//            seven_to_time.setText(one_to_time.getText());
//        }
////        if(!seven.isChecked())
//        else
//        {
//            seven_from_time.setText("From time");
//            seven_to_time.setText("To time");
//        }
//    }
//}
            case R.id.checkBox_monday:
                if(one.isChecked()) {
                    one_from_time.setEnabled(true);
                    one_to_time.setEnabled(true);
                }

                    else
                    {
                        one_from_time.setEnabled(false);
                        one_to_time.setEnabled(false);
                    }
            case R.id.checkBox_tues:
                if(two.isChecked()) {
                    two_from_time.setEnabled(true);
                    two_to_time.setEnabled(true);
                }

                else
                {
                    two_from_time.setEnabled(false);
                    two_to_time.setEnabled(false);
                }
            case R.id.checkBox_wed:
                if(three.isChecked()) {
                    three_from_time.setEnabled(true);
                    three_to_time.setEnabled(true);
                }

                else
                {
                    three_from_time.setEnabled(false);
                    three_to_time.setEnabled(false);
                }
            case R.id.checkBox_thursday:
                if(four.isChecked()) {
                    four_from_time.setEnabled(true);
                    four_to_time.setEnabled(true);
                }

                else
                {
                    four_from_time.setEnabled(false);
                    four_to_time.setEnabled(false);
                }
            case R.id.checkBox_friday:
                if(five.isChecked()) {
                    five_from_time.setEnabled(true);
                    five_to_time.setEnabled(true);
                }

                else
                {
                    five_from_time.setEnabled(false);
                    five_to_time.setEnabled(false);
                }
            case R.id.checkBox_saturday:
                if(six.isChecked()) {
                    six_from_time.setEnabled(true);
                    six_to_time.setEnabled(true);
                }

                else
                {
                    six_from_time.setEnabled(false);
                    six_to_time.setEnabled(false);
                }
            case R.id.checkBox_Sunday:
                if(seven.isChecked()) {
                    seven_from_time.setEnabled(true);
                    seven_to_time.setEnabled(true);
                }

                else
                {
                    seven_from_time.setEnabled(false);
                    seven_to_time.setEnabled(false);
                }
            default:
        }
    }
}