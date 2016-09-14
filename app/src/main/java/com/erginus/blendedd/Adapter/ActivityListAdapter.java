package com.erginus.blendedd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.erginus.blendedd.CategoryModel.ActivityModel;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by paramjeet on 1/9/15.
 */
public class ActivityListAdapter extends BaseAdapter {

    // Declare Variables
    private List<ActivityModel> catgryList;
    private final Context context;

    public ActivityListAdapter(Context context, List<ActivityModel> list) {
        this.context = context;
       this.catgryList=list;
    }

    @Override
    public int getCount() {
        return catgryList.size();
    }

    @Override
    public Object getItem(int position) {
        return catgryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return catgryList.indexOf(catgryList.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater;
        TextView txt_ordr_dt, txt_odr_price, txt_desc;
        ImageView imageView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_activity_item, parent,
                false);
//date=(TextView)itemView.findViewById(R.id.textView_date);
//    price=(TextView)itemView.findViewById(R.id.textView_price);
//        postid=(TextView)itemView.findViewById(R.id.textView15);
//           description=(TextView)itemView.findViewById(R.id.textView14);
//        image=(ImageView)itemView.findViewById(R.id.imageView6);

        txt_desc = (TextView) itemView.findViewById(R.id.textView_desc);
        txt_odr_price=(TextView)itemView.findViewById(R.id.textView_price);
        txt_ordr_dt=(TextView)itemView.findViewById(R.id.textView_date);
        imageView=(ImageView)itemView.findViewById(R.id.imageView6);
       txt_desc.setText(catgryList.get(position).getPostDescription());
        txt_odr_price.setText(catgryList.get(position).getPrice());

        Picasso.with(context).load(catgryList.get(position).getImage()).into(imageView);
        SimpleDateFormat inputFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d= null;
        try {
            d = inputFormat.parse(catgryList.get(position).getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat=
                new SimpleDateFormat("MMMM dd, yyyy");
        txt_ordr_dt.setText(outputFormat.format(d));
        return itemView;
    }


}