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
public class CountryListAdapter extends BaseAdapter {

        // Declare Variables
        private List<CategoryModel> countrylist;
        private final Context context;

        public CountryListAdapter(Context context, List<CategoryModel> list) {
            this.context = context;
            this.countrylist=list;
        }

        @Override
        public int getCount() {
            return countrylist.size();
        }

        @Override
        public Object getItem(int position) {
            return countrylist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return countrylist.indexOf(countrylist.get(position));
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater;
            TextView txtTitle;

            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.list_country, parent,false);

            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);

            txtTitle.setText(countrylist.get(position).getCountry_name());



            return itemView;
        }


}
