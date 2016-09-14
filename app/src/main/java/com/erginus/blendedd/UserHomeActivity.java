package com.erginus.blendedd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import com.erginus.blendedd.Adapter.ViewPagerAdapterHomeTabs;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.EndlessListView;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.SlidingTabLayout;
import com.erginus.blendedd.Commons.VolleySingleton;
import com.erginus.blendedd.Drawer_Effect.FlowingView;
import com.erginus.blendedd.Drawer_Effect.LeftDrawerLayout;
import com.erginus.blendedd.fragments.CategoriesFragment;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by nazer on 11/3/2015.
 */
public class UserHomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapterHomeTabs adapter;
    public static SlidingTabLayout tabs;
    CharSequence Titles[] = {"Home", "Categories"};
    String checkcreditcard,name;
    int Numboftabs = 2;
    ImageView img_menu, img_search, img_back;
    TextView txtVw_title, txtView;
    String[] title;
    //DrawerLayout drawerLayout;
    ListView listView;
    LinearLayout linearLayout;
    ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    String sign_up_info, about_us, how_does_it_work, faq, contact_us, post_id,stories, feedback, privacy, terms, cat_title;
    EndlessListView categoryItemListView;
    List<CategoryModel> categoryItemList, horizontalImageList,dayslist,buttonArraylist;
    CategoryItemsAdapter categoryItemsAdapter;
    String ptitle,postid,image, price, city_name, state_code, city_id, tiTle,tile_shadow,  post_desc, post_status, min_price,daily_price,
            weekly_price, monthly_price,day,from,to,current_day, current_time,pstname, button_text,button_value,button_id,button_price;
    EditText edt_search;
   // SwipeRefreshLayout mSwipeRefreshLayout;
    int page=0;
    private boolean mHaveMoreDataToLoad=true;
    SharedPreference getdata;


    static LeftDrawerLayout mLeftDrawerLayout;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);

        getdata=new SharedPreference(UserHomeActivity.this);
        getdata.setdrawer("1");
        if(!getdata.getproductid().equalsIgnoreCase(""))
        {
            if(getdata.getcreditcardnumber().equalsIgnoreCase(""))
            {
                Intent intent = new Intent(UserHomeActivity.this, User_Settings.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
            else {
                Intent intent = new Intent(UserHomeActivity.this, CategoryItemActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        }

        checkcreditcard = getdata.getcreditcardnumber();
        name = getdata.getfirstname();
        // Creating The Toolbar and setting it as the Toolbar for the activity
        FacebookSdk.sdkInitialize(UserHomeActivity.this);
        mLeftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.id_drawerlayout);
        FragmentManager fm = getSupportFragmentManager();
        MyMenuFragment mMenuFragment = (MyMenuFragment) fm.findFragmentById(R.id.id_container_menu);
        FlowingView mFlowingView = (FlowingView) findViewById(R.id.sv);
        if (mMenuFragment == null) {
            fm.beginTransaction().add(R.id.id_container_menu, mMenuFragment = new MyMenuFragment()).commit();
        }
        mLeftDrawerLayout.setFluidView(mFlowingView);
        mLeftDrawerLayout.setMenuFragment(mMenuFragment);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        txtVw_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtView = (TextView) toolbar.findViewById(R.id.text_title);
        horizontalImageList=new ArrayList<>();
        dayslist=new ArrayList<>();
        buttonArraylist=new ArrayList<>();
        edt_search = (EditText) toolbar.findViewById(R.id.editText_search);
        categoryItemListView = (EndlessListView) findViewById(R.id.listView_categry);
        listView = (ListView) findViewById(R.id.listview_drawer);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu_white);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLeftDrawerLayout.toggle();
            }
        });
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayShowHomeEnabled(true);
//            actionBar.setHomeButtonEnabled(true);
//            actionBar.setDisplayShowCustomEnabled(true);
//            actionBar.setDisplayShowTitleEnabled(false);
//
//        }
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter = new ViewPagerAdapterHomeTabs(getSupportFragmentManager(), Titles, Numboftabs);
        title = new String[]{"User Activities","Add Post","User Settings","About Us", "How Does It Work", "FAQ",
                "Contact Us", "Stories", "Feedback", "Privacy", "Terms","Logout"};
        categoryItemList = new ArrayList<>();
        ArrayAdapter<String> drawerAdapter = new ArrayAdapter<String>(
                getBaseContext(), R.layout.drawer_list_item, title
        );
        //mSwipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true);
        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

//        listView.setAdapter(drawerAdapter);
//        listView.setOnItemClickListener(new DrawerItemClickListener());
//        if (drawerLayout != null) {
//            drawerLayout.setDrawerShadow(R.drawable.list_back, GravityCompat.START);
//
//            mDrawerToggle = new ActionBarDrawerToggle(UserHomeActivity.this, drawerLayout,
//                    toolbar, R.string.drawer_open, R.string.drawer_close) {
//                public void onDrawerClosed(View view) {
//                    super.onDrawerClosed(view);
//                    invalidateOptionsMenu();
//                }
//
//                public void onDrawerOpened(View drawerView) {
//                    getSupportActionBar().setTitle(mDrawerTitle);
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    super.onDrawerOpened(drawerView);
//
//                    invalidateOptionsMenu();
//
//                }
//            };
//            drawerLayout.setDrawerListener(mDrawerToggle);
//
//        }

        // Assiging the Sliding Tab Layout View

        //GetStrings();
        categoryItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                categoriesItemDetail(categoryItemList.get(position).getPostId());

            }
        });


    }


   /* void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        mSwipeRefreshLayout.setRefreshing(false);
    }*/

//    @Override
//
//    protected void onPostCreate(Bundle savedInstanceState) {
//
//        super.onPostCreate(savedInstanceState);
//
//        // Sync the toggle state after onRestoreInstanceState has occurred.
//
//        mDrawerToggle.syncState();
//
//    }
//
//
//    @Override
//
//    public void onConfigurationChanged(Configuration newConfig) {
//
//        super.onConfigurationChanged(newConfig);
//
//        // Pass any configuration change to the drawer toggles
//
//        mDrawerToggle.onConfigurationChanged(newConfig);
//
//    }
//
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            drawerLayout.closeDrawer(linearLayout);
//            if (position == 0) {
//                Intent intent = new Intent(UserHomeActivity.this, UserActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 1) {
//                Intent postintent = new Intent(UserHomeActivity.this, Post_Categories.class);
//                startActivity(postintent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 2) {
//                Intent intent = new Intent(UserHomeActivity.this, User_Settings.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//
//            }
//            if (position == 3) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", about_us);
//                intent.putExtra("title", "About Us");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 4) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", how_does_it_work);
//                intent.putExtra("title", "How Does It Work");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 5) {
//
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", faq);
//                intent.putExtra("title", "FAQ");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//
//
//            if (position == 6) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", contact_us);
//                intent.putExtra("title", "Contact Us");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 7) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", stories);
//                intent.putExtra("title", "Stories");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 8) {
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", feedback);
//                intent.putExtra("title", "Feedback");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//
//
//            }
//            if (position == 9) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", privacy);
//                intent.putExtra("title", "Privacy");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//            if (position == 10) {
//
//                Intent intent = new Intent(UserHomeActivity.this, WebViewActivity.class);
//                intent.putExtra("url", terms);
//                intent.putExtra("title", "Terms");
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//            }
//
//            if (position == 11) {
//
//                Log.e("facebook logout", "logout");
//                getdata.cleardata();
//                LoginManager.getInstance().logOut();
//                Intent intent = new Intent(UserHomeActivity.this, HomeActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//
//
//            }
//
//        }
//    }

//    public void GetStrings() {
//        try {
//            final ProgressDialog pDialog = new ProgressDialog(UserHomeActivity.this);
//            pDialog.setMessage("Loading...");
//            pDialog.show();
//
//            Log.e("", "Strings" + MapAppConstant.API + "/strings");
//            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "/strings", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    pDialog.dismiss();
//                    try {
//                        JSONObject object = new JSONObject(response);
//                        String serverCode = object.getString("code");
//                        String serverMessage = object.getString("message");
//                        // Toast.makeText(HomeActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
//                        if (serverCode.equalsIgnoreCase("0")) {
//
//                        }
//                        if (serverCode.equalsIgnoreCase("1")) {
//                            try {
//
//                                if ("1".equals(serverCode)) {
//                                    JSONObject object1 = object.getJSONObject("data");
//                                    about_us = object1.getString("about_us");
//                                    sign_up_info = object1.getString("signup_info");
//                                    how_does_it_work = object1.getString("how_does_it_work");
//                                    faq = object1.getString("faq");
//                                    contact_us = object1.getString("contact_us");
//                                    stories = object1.getString("stories");
//                                    feedback = object1.getString("feedback");
//                                    privacy = object1.getString("privacy");
//                                    terms = object1.getString("terms");
//                                    Log.d("mcrtbkeurrrrrrrrrr", about_us + " " + sign_up_info + " " + how_does_it_work);
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    pDialog.dismiss();
//                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                        Toast.makeText(UserHomeActivity.this, "Timeout Error",
//                                Toast.LENGTH_LONG).show();
//                    } else if (error instanceof AuthFailureError) {
//                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
//                    } else if (error instanceof ServerError) {
//                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
//                    } else if (error instanceof NetworkError) {
//                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
//                    } else if (error instanceof ParseError) {
//                        VolleyLog.d("", "" + error.getMessage() + "," + error.toString());
//                    }
//                }
//            }
//            ) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//
//                    params.put("current_timestamp", "1");
//                    return params;
//                }
//            };
//            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//            sr.setShouldCache(true);
//            VolleySingleton.getInstance(UserHomeActivity.this.getApplicationContext()).addToRequestQueue(sr);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

    public void categoriesItemList(final String search, final int page) {
        try {
            final ProgressDialog pDialog = new ProgressDialog(UserHomeActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "search");
            StringRequest sr = new StringRequest(Request.Method.POST, MapAppConstant.API + "/search", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        //Toast.makeText(HomeActivity.this, "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {
                            categoryItemListView.loadMoreCompleat();
                            mHaveMoreDataToLoad=false;
                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {
                                int offset=0;
                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    if (jsonArray.length() > 0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            int  category_id= Integer.parseInt(jsonObject.getString("category_id"));
                                            cat_title = jsonObject.getString("post_title");
                                            image = jsonObject.getString("post_image_url");
                                            state_code = jsonObject.getString("state_code");
                                            city_id = jsonObject.getString("post_zipcode");
                                            city_name = jsonObject.getString("city_name");
                                            post_desc = jsonObject.getString("post_description");
                                            price = jsonObject.getString("post_display_price");
                                            post_id=jsonObject.getString("post_id");
                                            categoryItemList.add(categoryItemModel(cat_title, image, state_code, city_id, city_name, post_desc, price, post_id));
                                            if (category_id >= offset)
                                                offset = category_id;
                                        }
                                    }
                                    setCategoryList(categoryItemList);
                                }
                                if (categoryItemListView.getAdapter() == null) {
                                    categoryItemsAdapter = new CategoryItemsAdapter(UserHomeActivity.this, categoryItemList);
                                    categoryItemListView.setAdapter(categoryItemsAdapter);
                                    Log.d("i am here at adapter", "jhjgxbexey");

                                }
                                else
                                {
                                    categoryItemListView.loadMoreCompleat();
                                    categoryItemsAdapter.notifyDataSetChanged();

                                }

                            } catch (Exception e) {
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(UserHomeActivity.this, "Timeout Error",
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
                    params.put("search_term", search);
                    params.put("page", page+"");

                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000 * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(UserHomeActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public List<CategoryModel> getCategorylist() {
        return categoryItemList;
    }

    public void setCategoryList(List<CategoryModel> categry_list) {
        this.categoryItemList = categry_list;
    }

    private CategoryModel categoryItemModel(String title, String image, String statecode, String city_id, String city_name, String post_desc, String price, String post_id )
    {
        CategoryModel model = new CategoryModel();
        model.setCategoryTitle(title);
        model.setImage(image);
        model.setStateCode(statecode);
        model.setCityId(city_id);
        model.setCityName(city_name);
        model.setPostDescription(post_desc);
        model.setPrice(price);
        model.setPostId(post_id);
        return model;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        //searchView.setQueryHint(getString(R.string.search));
        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }
        searchView.clearFocus();
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            }
        });
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {

            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered

                return true;
            }

            public boolean onQueryTextSubmit(String query) {
                //Here u can get the value "query" which is entered in the search box.
                query=searchView.getQuery().toString();
                pager.setVisibility(View.GONE);
                tabs.setVisibility(View.GONE);
                getCategorylist().clear();
                searchView.clearFocus();
                categoryItemListView.setVisibility(View.VISIBLE);
               // mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                categoriesItemList(query, page);
                final String finalQuery = query;
               /* mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // Refresh items
                        page++;
                        categoriesItemList(finalQuery);
                        // Load complete
                        onItemsLoadComplete();

                    }
                });*/
                categoryItemListView.setOnLoadMoreListener(new EndlessListView.OnLoadMoreListener() {
                    @Override
                    public boolean onLoadMore() {
                        if (mHaveMoreDataToLoad) {
                            page++;
                            categoriesItemList(finalQuery,page);
                        } else {
                            Toast.makeText(UserHomeActivity.this, "No more data to load",
                                    Toast.LENGTH_SHORT).show();
                        }

                        return mHaveMoreDataToLoad;

                    }
                });



                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);


        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        MenuItemCompat.setOnActionExpandListener(searchMenuItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {

                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

                pager.setVisibility(View.VISIBLE);
                tabs.setVisibility(View.VISIBLE);
                categoryItemListView.setVisibility(View.GONE);
//                mSwipeRefreshLayout.setVisibility(View.GONE);
                return true;

            }
        }) ;

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setImageList(List<CategoryModel> categry_list) {
        this.horizontalImageList = categry_list;
    }
    public List<CategoryModel> getImagelist() {
        return horizontalImageList;
    }
    public void setbuttonArraylist(List<CategoryModel> btlist)
    {
        this.buttonArraylist=btlist;
    }
    public List<CategoryModel> getbuttonArraylist()
    {
        return buttonArraylist;
    }
    public void setallDayslist(List<CategoryModel> dayyslist)
    {
        this.dayslist=dayyslist;
    }
    public List<CategoryModel> getallDayslist()
    {
        return dayslist;
    }

    private CategoryModel categoryItemModel1( String image)
    {
        CategoryModel model=new CategoryModel();
        model.setImage(image);
        return  model;
    }
    private CategoryModel categoryItemModel2(String day,String from, String to,String current_day,String current_time)
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
            final ProgressDialog pDialog = new ProgressDialog(UserHomeActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "/" +"view");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"view", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
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
                                    ptitle=jsonObject.getString("post_title");
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
                            Intent intent=new Intent(UserHomeActivity.this, CategoryItemDetailActivity.class);
                            intent.putExtra("postid",postid);
                            intent.putExtra("title",ptitle);
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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(UserHomeActivity.this, "Timeout Error",
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
            VolleySingleton.getInstance(UserHomeActivity.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        dayslist.clear();buttonArraylist.clear();

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
    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);//
        if (mLeftDrawerLayout.isShownMenu()) {
            mLeftDrawerLayout.closeDrawer();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(false);
            builder.setMessage("Do you want to exit?");
            builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //if user pressed "yes", then he is allowed to exit from application
                    dialog.cancel();
                }
            });
            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    //finish();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
