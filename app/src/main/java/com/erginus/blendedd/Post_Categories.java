package com.erginus.blendedd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.erginus.blendedd.Adapter.CategoryListAdapter;
import com.erginus.blendedd.CategoryModel.CategoryModel;
import com.erginus.blendedd.Commons.MapAppConstant;
import com.erginus.blendedd.Commons.SharedPreference;
import com.erginus.blendedd.Commons.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nazer on 2/6/2016.
 */
public class Post_Categories extends AppCompatActivity
{
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;

    String category_id, category_name,category_slug,category_type,category_order,category_status;

    List<CategoryModel> categry_list;
    ListView listView;
    CategoryListAdapter categoryListAdapter;
    SharedPreference getdata;
    public static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_categories);

        Post_Categories.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getdata=new SharedPreference(Post_Categories.this);

        categry_list=new ArrayList<>();
        listView=(ListView)findViewById(R.id.listView);
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
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
        txt_title.setText("Post Categories");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                category_id = categry_list.get(position).getCategoryId();
                Intent intent = new Intent(Post_Categories.this, Add_Post_Contact_Detail.class);
                intent.putExtra("cat_id", category_id);

                Log.e("category id fragment", "" + category_id);
                startActivity(intent);
              Post_Categories.this.overridePendingTransition(0, 0);

            }
        });
        categories();

    }
    public List<CategoryModel> getCategorylist() {
        return categry_list;
    }

    public void setCategoryList(List<CategoryModel> categry_list) {
        this.categry_list = categry_list;
    }
    public void categories()
    {
        try {
            final ProgressDialog pDialog = new ProgressDialog(Post_Categories.this);
            pDialog.setMessage("Loading...");
            pDialog.show();

            Log.e("", "Strings" + MapAppConstant.API + "/categories");
            StringRequest sr = new StringRequest(Request.Method.POST,MapAppConstant.API+"/categories", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    pDialog.dismiss();
                    try {
                        JSONObject object = new JSONObject(response);
                        String serverCode = object.getString("code");
                        String serverMessage = object.getString("message");
                        //Toast.makeText(getActivity(), "" + serverMessage, Toast.LENGTH_SHORT).show();
                        if (serverCode.equalsIgnoreCase("0")) {

                        }
                        if (serverCode.equalsIgnoreCase("1")) {
                            try {

                                if ("1".equals(serverCode)) {
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    if(jsonArray.length()>0) {
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                                            category_id=jsonObject.getString("category_id");
                                            category_name=jsonObject.getString("category_name");
                                            category_slug=jsonObject.getString("category_slug");
                                            category_type=jsonObject.getString("category_type");
                                            category_order=jsonObject.getString("category_order");
                                            category_status=jsonObject.getString("category_status");

                                            categry_list.add(categoryModel(category_id,category_name,category_slug,category_type,category_order,category_status));

                                        }
                                    }
                                    setCategoryList(categry_list);
                                }
                                if(listView.getAdapter()==null){
                                    categoryListAdapter = new CategoryListAdapter(Post_Categories.this, categry_list);
                                    listView.setAdapter(categoryListAdapter);

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
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(Post_Categories.this, "Timeout Error",
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

                    params.put("current_timestamp", "1");
                    return params;
                }
            };
            sr.setRetryPolicy(new DefaultRetryPolicy(100000*2,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            sr.setShouldCache(true);
            VolleySingleton.getInstance(Post_Categories.this.getApplicationContext()).addToRequestQueue(sr);


        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private CategoryModel categoryModel(String id, String name, String slug, String type, String order, String status)
    {
        CategoryModel model=new CategoryModel();
        model.setCategoryId(id);
        model.setCategoryName(name);
        model.setCategorySlug(slug);
        model.setCategryType(type);
        model.setCategoryOrder(order);
        model.setCategoryStatus(status);
        return  model;
    }

}
