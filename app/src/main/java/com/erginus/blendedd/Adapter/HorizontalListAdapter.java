package com.erginus.blendedd.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by paramjeet on 8/9/15.
 */
//public class HorizontalListAdapter extends BaseAdapter {
//
//    // Declare Variables
//    private List<CategoryModel> catgryList;
//    private final Context context;
//
//    public HorizontalListAdapter(Context context, List<CategoryModel> list) {
//        this.context = context;
//        this.catgryList=list;
//    }
//
//    @Override
//    public int getCount() {
//        return catgryList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return catgryList.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return catgryList.indexOf(catgryList.get(position));
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater;
//
//        ImageView imageView;
//
//        inflater = (LayoutInflater) context
//                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View itemView = inflater.inflate(R.layout.list_category_image, parent,
//                false);
//
//        imageView=(ImageView)itemView.findViewById(R.id.image_product);
//        Picasso.with(context).load(catgryList.get(position).getImage()).into(imageView);
//
//        return itemView;
//    }
//
//
//}
public class HorizontalListAdapter extends PagerAdapter {
    private List<CategoryModel> catgryList;
        private final Context context;

    public HorizontalListAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.catgryList=list;
    }
    @Override
    public int getCount() {
        return catgryList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        Picasso.with(context).load(catgryList.get(position).getImage()).into(imageView);

        ((ViewPager) container).addView(imageView, 0);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String image_big=catgryList.get(position).getImage();
                dialog_image(image_big);
            }
        });

        return imageView;
    }
    public  void dialog_image(String image_big)
    {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.layout_large_image);
        ImageView imageButton=(ImageView)dialog.findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ImageView image = (ImageView) dialog.findViewById(R.id.imageview);

// !!! Do here setBackground() instead of setImageDrawable() !!! //
        Picasso.with(context).load(image_big).into(image);

// Without this line there is a very small border around the image (1px)
// In my opinion it looks much better without it, so the choice is up to you.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

// Show the dialog
        dialog.show();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}