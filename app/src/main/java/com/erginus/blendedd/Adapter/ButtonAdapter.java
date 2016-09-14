package com.erginus.blendedd.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.erginus.blendedd.CategoryItemDetailActivity;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;

import java.util.List;

/**
 * Created by nazer on 10/31/2015.
 */
public class ButtonAdapter extends BaseAdapter {
  private List<CategoryModel> buttonlist;

    private final Context context;
    public ButtonAdapter(Context context, List<CategoryModel> buttonlist) {
        this.context=context;
        this.buttonlist=buttonlist;
    }

    @Override
    public int getCount() {
        return buttonlist.size();
    }

    @Override
    public Object getItem(int position) {
        return buttonlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return buttonlist.indexOf(buttonlist.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.adapterbutton,parent,false);
       // ImageView btn=(ImageView)convertView.findViewById(R.id.button);
        TextView tv=(TextView)convertView.findViewById(R.id.text);

        String text=buttonlist.get(position).getbutton_text();
        String value=buttonlist.get(position).getbutton_value();

        String bothtext=text+"\n"+value;
        tv.setText(bothtext);
        return convertView;
    }
}
