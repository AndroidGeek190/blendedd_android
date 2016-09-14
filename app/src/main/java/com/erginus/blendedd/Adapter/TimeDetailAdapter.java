package com.erginus.blendedd.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;

import java.util.List;

/**
 * Created by nazer on 10/29/2015.
 */
public class TimeDetailAdapter extends BaseAdapter {
    private List<CategoryModel> dayList;

        private final Context context;

    public TimeDetailAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.dayList=list;

    }

    @Override
    public int getCount() {
        return dayList.size();
    }

    @Override
    public Object getItem(int position) {
        return dayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dayList.indexOf(dayList.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tday,tfrom,tto;
        LayoutInflater inflater;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.time_detail_list, parent,
                false);

        tday=(TextView)convertView.findViewById(R.id.day_name);
        tfrom=(TextView)convertView.findViewById(R.id.from_time);
        tto=(TextView)convertView.findViewById(R.id.to_time);
        String current_day,current_time;
        current_day=dayList.get(position).getcurrent_day();
        current_time=dayList.get(position).getcurrent_time();


        if((current_day.equalsIgnoreCase("true")))
        {
            tday.setTextColor(0xffff0000);
            tfrom.setTextColor(0xffff0000);
            tto.setTextColor(0xffff0000);
        }
        else
        {
            tday.setTextColor(0xff444444);
            tfrom.setTextColor(0xff444444);
            tto.setTextColor(0xff444444);
        }
        tday.setText(dayList.get(position).getdays());
        tfrom.setText(dayList.get(position).getfrom());
        tto.setText(dayList.get(position).getto());
        return convertView;
    }
}
