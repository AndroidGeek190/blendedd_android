package com.erginus.blendedd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paramjeet on 1/9/15.
 */
public class CategoryItemsAdapter extends BaseAdapter {

    // Declare Variables
    private List<CategoryModel> catgryList;
    private final Context context;

    public CategoryItemsAdapter(Context context, List<CategoryModel> list) {
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
        TextView txtTitle, txt_price, txt_code, txt_desc;
        ImageView imageView;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_category_item, parent,
                false);

        txt_price = (TextView) itemView.findViewById(R.id.textView_price);
        txt_code = (TextView) itemView.findViewById(R.id.textView_code);
        txtTitle = (TextView) itemView.findViewById(R.id.textView_title);
        imageView=(ImageView)itemView.findViewById(R.id.image_product);
        txt_desc=(TextView)itemView.findViewById(R.id.textView_desc);
        txtTitle.setText(catgryList.get(position).getCategoryTitle());
        txt_price.setText(catgryList.get(position).getPrice());
        txt_code.setText(catgryList.get(position).getCityName()+" "+catgryList.get(position).getStateCode()+" "+catgryList.get(position).getCityId());
        Picasso.with(context).load(catgryList.get(position).getImage()).into(imageView);
txt_desc.setText(catgryList.get(position).getPostDescription());


        return itemView;
    }


}