package com.erginus.blendedd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.erginus.blendedd.Adapter.ButtonAdapter;
import com.erginus.blendedd.Adapter.HorizontalListAdapter;
import com.erginus.blendedd.Adapter.TimeDetailAdapter;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.ExpandableHeightGridView;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.fragments.User_Personal_Setting;



import java.util.ArrayList;
import java.util.List;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.PageIndicator;
public class CategoryItemDetailActivity extends AppCompatActivity{

    public  static ImageView post_image;
    // public  static Button btn_deal1, btn_deal2,btn_deal3,btn_deal4;
    public  static TextView txt_avail, txtTitle, txt_description,txt_pst,txt_day,txt_from,txt_to;
    String  title, tile_shadow,  post_desc, post_status, min_price,daily_price,image_big,
            weekly_price, monthly_price,pstdata,post_id;

    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout  ll_navigation;
    List<CategoryModel> imageList,days_list,buttonlist;
    HorizontalListAdapter horizontalListAdapter;
    TimeDetailAdapter timeDetailAdapter;
    int pos=0;
    ListView listdays;
    ViewPager viewPager;
    ButtonAdapter buttonAdapter;
    //GridView gridview;
    ExpandableHeightGridView gridview;
    String button_price;
    String button_display_price;
    String button_id;
    PageIndicator mIndicator;
    SharedPreference getdata;
    public static SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_detail);
        getdata = new SharedPreference(CategoryItemDetailActivity.this);

        imageList=new ArrayList<>();
        days_list=new ArrayList<>();
        buttonlist=new ArrayList<>();
        days_list.clear();

        gridview=(ExpandableHeightGridView)findViewById(R.id.gridview);
        gridview.setExpanded(true);
        txt_avail=(TextView)findViewById(R.id.txt_availabilty);
        txtTitle=(TextView)findViewById(R.id.text_title_post);
        txt_description=(TextView)findViewById(R.id.text_description);
        txt_pst=(TextView)findViewById(R.id.pst);
        listdays=(ListView)findViewById(R.id.days);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        CirclePageIndicator indicator = (CirclePageIndicator)findViewById(R.id.indicator);

        title=getIntent().getStringExtra("title");
        post_id=getIntent().getStringExtra("postid");

        tile_shadow=getIntent().getStringExtra("titleShadow");
        post_desc=getIntent().getStringExtra("desc");
        pstdata=getIntent().getStringExtra("pst");
        post_status=getIntent().getStringExtra("status");
        min_price=getIntent().getStringExtra("min_price");
        daily_price=getIntent().getStringExtra("daily_price");
        weekly_price=getIntent().getStringExtra("weekly_price");
        monthly_price=getIntent().getStringExtra("monthly_price");
        imageList= (List<CategoryModel>) getIntent().getSerializableExtra("image");
        days_list=(List<CategoryModel>)getIntent().getSerializableExtra("days");
        buttonlist=(List<CategoryModel>)getIntent().getSerializableExtra("button");
        txt_pst.setText(pstdata);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtVw_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        txtVw_title.setVisibility(View.GONE);
        txt_title=(TextView)toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);
        img_back=(ImageView)toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        ll_navigation=(LinearLayout)toolbar.findViewById(R.id.ll_navi);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days_list.clear();
                if(!getdata.getproductid().equalsIgnoreCase(""))
                {
                    if(getdata.getuserstatus().equalsIgnoreCase("1")) {
                        preferences = PreferenceManager.getDefaultSharedPreferences(CategoryItemDetailActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("product_id");
                        editor.commit();
                        Intent intent = new Intent(CategoryItemDetailActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        preferences = PreferenceManager.getDefaultSharedPreferences(CategoryItemDetailActivity.this);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.remove("product_id");
                        editor.commit();
                        finish();
                    }

                }
                else {
                    preferences = PreferenceManager.getDefaultSharedPreferences(CategoryItemDetailActivity.this);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("product_id");
                    editor.commit();
                    finish();
                }
            }
        });
        txt_title.setText("Category Item Details");
        txtTitle.setText(tile_shadow);
        txt_description.setText(post_desc);

        if(post_status.equals("1"))
        {
            txt_avail.setText("Available");
        }
        else {
            txt_avail.setText("Rented");
        }

        timeDetailAdapter=new TimeDetailAdapter(CategoryItemDetailActivity.this,days_list);
        listdays.setAdapter(timeDetailAdapter);

        setListViewHeightBasedOnChildren(listdays);

        buttonAdapter=new ButtonAdapter(CategoryItemDetailActivity.this,buttonlist);
        gridview.setAdapter(buttonAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                String checkcreditcard = getdata.getcreditcardnumber();
                String name = getdata.getfirstname();

                button_price = buttonlist.get(position).getbutton_price();
                button_display_price = buttonlist.get(position).getbutton_value();
                button_id = buttonlist.get(position).getbutton_id();

                if (name.equalsIgnoreCase("null")&&checkcreditcard.equalsIgnoreCase("")) {
                    Log.e("name", name + "" + checkcreditcard);
                    getdata.setproductid(post_id);
                    Intent intent = new Intent(CategoryItemDetailActivity.this, HomeActivity.class);
                    intent.putExtra("tab_index", "2");
                    startActivity(intent);
                    overridePendingTransition(0, 0);

                }
                if((!name.equalsIgnoreCase("null"))&&(checkcreditcard.equalsIgnoreCase("")))
                {
                    getdata.setproductid(post_id);
                    Intent intent = new Intent(CategoryItemDetailActivity.this, User_Settings.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

                if((!name.equalsIgnoreCase("null"))&&(!checkcreditcard.equalsIgnoreCase("")))
                {



                    Intent intent = new Intent(CategoryItemDetailActivity.this, CheckoutActivity.class);
                    intent.putExtra("button_id", button_id);
                    intent.putExtra("button_display_price", button_display_price);
                    intent.putExtra("title", tile_shadow);
                    intent.putExtra("postid", post_id);
                    intent.putExtra("button_price", button_price);

                    Log.e("Button Display price", button_display_price);
                    Log.e("post id",post_id);
                    Log.e("Button Display price",tile_shadow);
                    Log.e("Button Display price",button_price);
                    Log.e("Button Display price",button_id);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }

            }
        });


        horizontalListAdapter  = new HorizontalListAdapter(CategoryItemDetailActivity.this,imageList);
        viewPager.setAdapter(horizontalListAdapter);
        //viewPager.setOffscreenPageLimit(horizontalListAdapter.getCount());
        //A little space between pages
        //    viewPager.setPageMargin(-170);
        //viewPager.setHorizontalFadingEdgeEnabled(true);
        //viewPager.setFadingEdgeLength(90);

        //If hardware acceleration is enabled, you should also remove
        // clipping on the pager for its children.
        //viewPager.setClipChildren(false);

        mIndicator = indicator;
        indicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        indicator.setPageColor(0xFF888888);
        indicator.setFillColor(0x880000FF);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

//    @Override
//    public void onBackPressed() {
//       onResume();
//
//            Intent intent = new Intent(CategoryItemDetailActivity.this, CheckoutActivity.class);
//            intent.putExtra("button_id", button_id);
//            intent.putExtra("button_display_price", button_display_price);
//            intent.putExtra("title", tile_shadow);
//            intent.putExtra("postid", postid);
//            intent.putExtra("button_price", button_price);
//            startActivity(intent);
//            overridePendingTransition(0, 0);
//        }



    @Override
    public void onBackPressed() {
        onResume();

    }
}