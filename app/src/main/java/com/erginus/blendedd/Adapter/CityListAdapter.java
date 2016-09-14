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
 * Created by nazer on 10/23/2015.
 */
public class CityListAdapter extends BaseAdapter {

    // Declare Variables
    private List<CategoryModel> country_city_list;
    private final Context context;

    public CityListAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.country_city_list=list;
    }

    @Override
    public int getCount() {
        return country_city_list.size();
    }

    @Override
    public Object getItem(int position) {
        return country_city_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return country_city_list.indexOf(country_city_list.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        TextView txtTitle;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_state, parent,false);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

        txtTitle.setText(country_city_list.get(position).getcountry_city_name());



        return itemView;
    }
}
