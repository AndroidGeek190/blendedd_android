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
public class StateListAdapter extends BaseAdapter {

    // Declare Variables
    private List<CategoryModel> statelist;
    private final Context context;

    public StateListAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.statelist = list;
    }

    @Override
    public int getCount() {
        return statelist.size();
    }

    @Override
    public Object getItem(int position) {
        return statelist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return statelist.indexOf(statelist.get(position));
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        TextView txtTitle;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.list_country, parent, false);

        txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

        txtTitle.setText(statelist.get(position).getstate_name());


        return itemView;
    }
}