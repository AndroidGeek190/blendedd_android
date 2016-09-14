package com.erginus.blendedd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;

import java.util.List;

/**
 * Created by paramjeet on 1/9/15.
 */
public class CategoryListAdapter extends BaseAdapter {

    // Declare Variables
    private List<CategoryModel> catgryList;
    private final Context context;

    public CategoryListAdapter(Context context, List<CategoryModel> list) {
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
        TextView txtTitle;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_category, parent,false);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

       txtTitle.setText(catgryList.get(position).getCategoryName());



        return itemView;
    }


}