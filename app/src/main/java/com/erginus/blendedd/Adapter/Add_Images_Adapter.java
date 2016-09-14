package com.erginus.blendedd.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import com.erginus.blendedd.Add_Post_Images;
import com.erginus.blendedd.R;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazer on 2/8/2016.
 */
public class Add_Images_Adapter extends BaseAdapter {
    private List<File> buttonlist = new ArrayList<File>();
    Add_Post_Images add_post_images;
    private final Context context;

    public Add_Images_Adapter(Context context, List<File> listpost) {
        this.context = context;
        this.buttonlist = listpost;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.add_post_images_custom, parent, false);
        Button tv = (Button) convertView.findViewById(R.id.remove1);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);
        //RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio1);
        imageView.setImageURI(Uri.fromFile(buttonlist.get(position)));

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_post_images.listone.remove(buttonlist.get(position));
                notifyDataSetChanged();
                add_post_images.change();
            }
        });
        return convertView;
    }
}

