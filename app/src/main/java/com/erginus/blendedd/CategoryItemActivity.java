package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.erginus.blendedd.Adapter.CategoryItemsAdapter;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.EndlessListView;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryItemActivity extends AppCompatActivity {
    //ListView categoryItemListView;
    EndlessListView categoryItemListView;
    List<CategoryModel>  categoryItemList, horizontalImageList,dayslist,buttonArraylist;
    CategoryItemsAdapter categoryItemsAdapter;
    String  price, city_name,state_code, city_id, category_id, post_id,postid,title, image, tile_shadow,  post_desc, post_status, min_price,daily_price,
            weekly_price, monthly_price,pstname,day,from,to,button_text,button_value,button_id,button_price,current_day,current_time;
    Toolbar toolbar;
    ImageView  img_back;
    TextView txtVw_title,txt_title;
    LinearLayout ll_navigation;

    int page=0;
    SharedPreference getdata;
    private boolean mIsLoading=true;
    String posstid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);
        getdata=new SharedPreference(CategoryItemActivity.this);

        if(!getdata.getproductid().equalsIgnoreCase("null"))
        {
            categoriesItemDetail(getdata.getproductid());
        }

        category_id=getIntent().getStringExtra("cat_id");

        categoryItemList=new ArrayList<>();
        horizontalImageList=new ArrayList<>();
        dayslist=new ArrayList<>();
        buttonArraylist=new ArrayList<>();
        //categoryItemListView=(ListView)findViewById(R.id.listView_categry);
        categoryItemListView=(EndlessListView)findViewById(R.id.listView_categry);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        txtVw_title=(TextView)toolbar.findViewById(R.id.toolbar_title);
        img_back=(ImageView)toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        txtVw_title.setVisibility(View.GONE);
        txt_title=(TextView)toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);

        ll_navigation=(LinearLayout)toolbar.findViewById(R.id.ll_navi);
        //mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        txt_title.setText("Categories");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        categoriesItemList(category_id, page);
       /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();

            }
        });*/
        categoryItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            posstid=categoryItemList.get(position).getPostId();
                categoriesItemDetail(posstid);
                Log.e("Post id on click ", "" + categoryItemList.get(position).getPostId());

            }
        });
        categoryItemListView.setOnLoadMoreListener(new EndlessListView.OnLoadMoreListener() {
            @Override
            public boolean onLoadMore() {
                if (mIsLoading) {
                     page++;
                    categoriesItemList(category_id, page);
                } else {
                    Toast.makeText(CategoryItemActivity.this, "No more data to load",
                            Toast.LENGTH_SHORT).show();
                }

                return mIsLoading;

            }
        });
    }
   /* void refreshItems() {
        // Load items
        // ...
        page++;
        categoriesItemList(category_id, page);
        // Load complete
        onItemsLoadComplete();
    }*/

  /*  void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }*/
    public void categoriesItemList(final String category_id,final int page)
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(CategoryItemActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "/search");

            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"search", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    ;
                    Log.d("", ".......response====" + response.toString());

                    ////////
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                     //   Toast.makeText(CategoryItemActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {
                            categoryItemListView.loadMoreCompleat();
                            mIsLoading=false;
                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {
                                int offset=0;

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    if(jsonArray.length()>0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                                            int  category_id= Integer.parseInt(jsonObject.getString("category_id"));
                                            title=jsonObject.getString("post_title");
                                            image=jsonObject.getString("post_image_url");
                                            state_code=jsonObject.getString("state_code");
                                            city_id=jsonObject.getString("post_zipcode");
                                            city_name=jsonObject.getString("city_name");
                                            post_desc=jsonObject.getString("post_description");
                                            price=jsonObject.getString("post_display_price");
                                            post_id=jsonObject.getString("post_id");
                                            categoryItemList.add(categoryItemModel(title, image, state_code, city_id, city_name, post_desc, price, post_id));
                                            if (category_id >= offset)
                                                offset = category_id;
                                        }
                                    }
                                    setCategoryList(categoryItemList);
                                }
                                if(categoryItemListView.getAdapter()==null){
                                    categoryItemsAdapter = new CategoryItemsAdapter(CategoryItemActivity.this, categoryItemList);
                                    categoryItemListView.setAdapter(categoryItemsAdapter);

                                }
                                else {
                                    categoryItemListView.loadMoreCompleat();
                                    categoryItemsAdapter.notifyDataSetChanged();

                                }


                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    ;
                    //  VolleyLog.d("", "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(CategoryItemActivity.this, "Timeout Error",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ServerError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof NetworkError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ParseError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("categories_id", category_id);
                    params.put("search_term", "");
                    params.put("page", page+"");

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(CategoryItemActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    public List<CategoryModel> getCategorylist() {
        return categoryItemList;
    }
    public List<CategoryModel> getImagelist() {
        return horizontalImageList;
    }
    public void setImageList(List<CategoryModel> categry_list) {
        this.horizontalImageList = categry_list;
    }
    public void setCategoryList(List<CategoryModel> categry_list) {
        this.categoryItemList = categry_list;
    }
    public void setallDayslist(List<CategoryModel> dayyslist)
    {
        this.dayslist=dayyslist;
    }
    public List<CategoryModel> getallDayslist()
    {
        return dayslist;
    }
    public void setbuttonArraylist(List<CategoryModel> btlist)
    {
        this.buttonArraylist=btlist;
    }
    public List<CategoryModel> getbuttonArraylist()
    {
        return buttonArraylist;
    }

    private CategoryModel categoryItemModel(String title, String image, String statecode, String city_id, String city_name,String post_desc, String price, String post_id)
    {
        CategoryModel model=new CategoryModel();
        model.setCategoryTitle(title);
        model.setImage(image);
        model.setStateCode(statecode);
        model.setCityId(city_id);
        model.setCityName(city_name);
        model.setPostDescription(post_desc);
        model.setPrice(price);
        model.setPostId(post_id);

        return  model;
    }
    private CategoryModel categoryItemModel1( String image)
    {
        CategoryModel model=new CategoryModel();
        model.setImage(image);
        return  model;
    }
    private CategoryModel categoryItemModeldays(String day,String from, String to,String current_day,String current_time)
    {
        CategoryModel model=new CategoryModel();
        model.setdays(day);
        model.setfrom(from);
        model.setto(to);
        model.setcurrent_day(current_day);
        model.setcurrent_time(current_time);
        return  model;
    }
    private CategoryModel categoryItemModel3(String text, String value,String button_id,String button_price)
    {
        CategoryModel model=new CategoryModel();
        model.setbutton_text(text);
        model.setbutton_value(value);
        model.setbutton_price(button_price);
        model.setButton_id(button_id);
        return  model;
    }
    public void categoriesItemDetail(final String post_id)
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(CategoryItemActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "/view");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"view", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    Log.d("", ".......response====" + response.toString());

                    ////////
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {
                                getImagelist().clear();
                                if ("1".equals(serverCode)) {
                                    JSONObject jsonObject=object.getJSONObject("data");

                                    postid=jsonObject.getString("post_id");
                                    title=jsonObject.getString("post_title");
                                    tile_shadow=jsonObject.getString("post_title_shadow");
                                    state_code=jsonObject.getString("state_code");
                                    city_id=jsonObject.getString("post_zipcode");
                                    city_name=jsonObject.getString("city_name");
                                    post_desc=jsonObject.getString("post_description");
                                    post_status=jsonObject.getString("post_status");
                                    min_price=jsonObject.getString("post_min_price");
                                    daily_price=jsonObject.getString("post_daily_price");
                                    weekly_price=jsonObject.getString("post_weekly_price");
                                    monthly_price=jsonObject.getString("post_monthly_price");
                                    pstname=jsonObject.getString("time_zone_slug");

                                    JSONArray jsonArray=jsonObject.getJSONArray("post_images_array");
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            image=jsonObject1.getString("post_image_url");
                                            horizontalImageList.add(categoryItemModel1(image));
                                        }}

                                    setImageList(horizontalImageList);

                                    JSONArray arrayday=jsonObject.getJSONArray("post_time_availability_array");

                                    if (arrayday.length() > 0) {
                                        for (int i = 0; i < arrayday.length(); i++) {
                                            JSONObject jsonObject2 = arrayday.getJSONObject(i);
                                            day=jsonObject2.getString("post_availability_day");
                                            from=jsonObject2.getString("post_availability_from");
                                            to=jsonObject2.getString("post_availability_to");
                                            current_day=jsonObject2.getString("post_availability_current_day");
                                            current_time=jsonObject2.getString("post_availabile_current_time");
                                            dayslist.add(categoryItemModeldays(day, from, to, current_day, current_time));
                                        }
                                    }
                                    setallDayslist(dayslist);
                                    JSONArray arraybutton=jsonObject.getJSONArray("pricing_buttons");
                                    if (arraybutton.length() > 0) {
                                        for (int i = 0; i < arraybutton.length(); i++) {
                                            JSONObject jsonObject2 = arraybutton.getJSONObject(i);
                                            button_text=jsonObject2.getString("price_button_text");
                                            button_value=jsonObject2.getString("price_button_display_value");
                                            button_id=jsonObject2.getString("pricing_options_id");
                                            button_price=jsonObject2.getString("price_button_value");
                                            buttonArraylist.add(categoryItemModel3(button_text, button_value,button_id,button_price));
                                        }
                                    }
                                    setbuttonArraylist(buttonArraylist);
                                }
                            }

                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            Intent intent=new Intent(CategoryItemActivity.this, CategoryItemDetailActivity.class);
                            intent.putExtra("postid",postid);
                            intent.putExtra("title",title);
                            intent.putExtra("titleShadow",tile_shadow);
                            intent.putExtra("desc", post_desc);
                            intent.putExtra("image", (Serializable) horizontalImageList);
                            intent.putExtra("days", (Serializable) dayslist);
                            intent.putExtra("button", (Serializable) buttonArraylist);
                            intent.putExtra("pst", pstname);
                            intent.putExtra("status", post_status);
                            intent.putExtra("min_price", min_price);
                            intent.putExtra("daily_price", daily_price);
                            intent.putExtra("weekly_price", weekly_price);
                            intent.putExtra("monthly_price", monthly_price);

                            intent.putExtra("status",post_status);
                            intent.putExtra("min_price",min_price);
                            intent.putExtra("daily_price",daily_price);
                            intent.putExtra("weekly_price",weekly_price);
                            intent.putExtra("monthly_price",monthly_price);
                            startActivity(intent);
                            overridePendingTransition(0,0);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    pDialog.dismiss();
                    //  VolleyLog.d("", "Error: " + error.getMessage());
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(CategoryItemActivity.this, "Timeout Error",
                                Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ServerError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof NetworkError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    } else if (error instanceof ParseError) {
                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
                    }
                }
            }
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("post_id", post_id);


                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(CategoryItemActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        dayslist.clear();
        buttonArraylist.clear();
    }
}
