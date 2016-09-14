package com.erginus.blendedd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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

import com.erginus.blendedd.Adapter.Add_Images_Adapter;
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

/**
 * Created by nazer on 2/6/2016.
 */
public class Add_Post_Images extends AppCompatActivity {
    Toolbar toolbar;
    ImageView img_back;
    TextView txtVw_title, txt_title;
    LinearLayout ll_navigation;
    private static final int REQUEST_CODE_CAPTURE_IMAGE = 2500;
    private static final int PHOTO_PICKER_ID = 1;
    String filename;
    File f=null;
    Uri imageUri;
   // static Add_Images_Adapter add_images_adapter;
    public static List listpost = new ArrayList();
    public static int a = 0;
    ListView lv;
   public static List<File> listone=new ArrayList<File>();
    public File fil;
    int value_text=0;
    int picdata;
    Button bt_select;
    public static Button bt_upload;
    Add_Images_Adapter add_images_adapter;

ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6;
   // RadioButton radioButton1,radioButton2,radioButton3,radioButton4,radioButton5,radioButton6;
    TextView tremove1,tremove2,tremove3,tremove4,tremove5,tremove6;
    LinearLayout layout1,layout2,layout3,layout4,layout5,layout6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_images);
        lv = (ListView) findViewById(R.id.list);


        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        txtVw_title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        txtVw_title.setVisibility(View.GONE);
        txt_title = (TextView) toolbar.findViewById(R.id.text_title);
        txt_title.setVisibility(View.VISIBLE);
        img_back = (ImageView) toolbar.findViewById(R.id.imageView_back);
        img_back.setVisibility(View.VISIBLE);
        ll_navigation = (LinearLayout) toolbar.findViewById(R.id.ll_navi);
        setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        txt_title.setText("Post Images");
        ll_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        imageView1=(ImageView)findViewById(R.id.image1);
//        imageView2=(ImageView)findViewById(R.id.image2);
//        imageView2.setVisibility(View.GONE);
//        imageView3=(ImageView)findViewById(R.id.image3);
//        imageView3.setVisibility(View.GONE);
//        imageView4=(ImageView)findViewById(R.id.image4);
//        imageView4.setVisibility(View.GONE);
//        imageView5=(ImageView)findViewById(R.id.image5);
//        imageView5.setVisibility(View.GONE);
//        imageView6=(ImageView)findViewById(R.id.image6);
//        imageView6.setVisibility(View.GONE);
//
//        layout1=(LinearLayout)findViewById(R.id.one);
//        layout2=(LinearLayout)findViewById(R.id.two);
//        layout3=(LinearLayout)findViewById(R.id.three);
//        layout4=(LinearLayout)findViewById(R.id.four);
//        layout5=(LinearLayout)findViewById(R.id.five);
//        layout6=(LinearLayout)findViewById(R.id.six);
//
//
////        radioButton1=(RadioButton)findViewById(R.id.radio1);
////
////        radioButton2=(RadioButton)findViewById(R.id.radio2);
////        radioButton2.setVisibility(View.GONE);
////        radioButton3=(RadioButton)findViewById(R.id.radio3);
////        radioButton3.setVisibility(View.GONE);
////        radioButton4=(RadioButton)findViewById(R.id.radio4);
////        radioButton4.setVisibility(View.GONE);
////        radioButton5=(RadioButton)findViewById(R.id.radio5);
////        radioButton5.setVisibility(View.GONE);
////        radioButton6=(RadioButton)findViewById(R.id.radio6);
////        radioButton6.setVisibility(View.GONE);
//
//        tremove1=(TextView)findViewById(R.id.remove1);
//        tremove2=(TextView)findViewById(R.id.remove2);
//        tremove2.setVisibility(View.GONE);
//        tremove3=(TextView)findViewById(R.id.remove3);
//        tremove3.setVisibility(View.GONE);
//        tremove4=(TextView)findViewById(R.id.remove4);
//        tremove4.setVisibility(View.GONE);
//        tremove5=(TextView)findViewById(R.id.remove5);
//        tremove5.setVisibility(View.GONE);
//        tremove6=(TextView)findViewById(R.id.remove6);
//        tremove6.setVisibility(View.GONE);
//
//
//         imageView1.setOnClickListener(this);
//        imageView2.setOnClickListener(this);
//        imageView3.setOnClickListener(this);
//        imageView4.setOnClickListener(this);
//        imageView5.setOnClickListener(this);
//        imageView6.setOnClickListener(this);
////        radioButton1.setOnClickListener(this);
////        radioButton2.setOnClickListener(this);
////        radioButton3.setOnClickListener(this);
////        radioButton4.setOnClickListener(this);
////        radioButton5.setOnClickListener(this);
////        radioButton6.setOnClickListener(this);
//
//         tremove1.setOnClickListener(this);
//        tremove2.setOnClickListener(this);
//        tremove3.setOnClickListener(this);
//        tremove4.setOnClickListener(this);
//        tremove5.setOnClickListener(this);
//        tremove6.setOnClickListener(this);


    bt_select=(Button)findViewById(R.id.post_image);
        bt_upload=(Button)findViewById(R.id.up);
        bt_upload.setVisibility(View.GONE);

        bt_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listone.size()==6)
                {
                    Toast.makeText(Add_Post_Images.this, "You Can Upload Maximum Six Images", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog();
                }
            }
        });



//        listpost.add(a);
//


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                change();
//                // dialog();
//                Log.e("list position", "" + position);
//            }
//        });
//
//    }
//
//    public static void change() {
//        add_images_adapter.notifyDataSetChanged();
    }
public static void change()
{
    if(listone.size()>=1)
    {
        bt_upload.setVisibility(View.VISIBLE);
        Log.e("size", "" + listone.size());
    }
    else {
        Log.e("size hoja",""+listone.size());
        bt_upload.setVisibility(View.GONE);
    }
}
    @Override
    protected void onResume() {
        super.onResume();
        if(listone.size()>=1)
        {
            bt_upload.setVisibility(View.VISIBLE);
            Log.e("size", "" + listone.size());
        }

    }

    public void dialog() {
        final Dialog dialog = new Dialog(Add_Post_Images.this);
        dialog.setTitle("Upload From");
        dialog.setContentView(R.layout.dialog_pop_up_gallery_camera);

        dialog.setTitle("Select an Option...");
        TextView txt_gallry = (TextView) dialog.findViewById(R.id.textView_gallery);
        TextView txt_camera = (TextView) dialog.findViewById(R.id.textView_camera);

        txt_gallry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                i.setType("image/*");
                startActivityForResult(i, PHOTO_PICKER_ID);
            }
        });
        txt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();


                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                File fil = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(fil));
                startActivityForResult(cameraIntent, REQUEST_CODE_CAPTURE_IMAGE);
            }
        });
        dialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {
                case REQUEST_CODE_CAPTURE_IMAGE:

                    if (requestCode == REQUEST_CODE_CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {

                        fil = new File(Environment.getExternalStorageDirectory().toString());
                        for (File temp : fil.listFiles()) {
                            if (temp.getName().equals("temp.jpg")) {
                                fil = temp;
                                break;
                            }
                        }
                        Bitmap bitmap = null;

                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        Bitmap bmp = BitmapFactory.decodeFile(fil.getAbsolutePath(), bitmapOptions);
//
                        Log.e("edit", "new image path is " + fil.getAbsolutePath());
                        Log.e("bitmap", "" + bmp);
                        Log.e("Result Ok", "" + data);
                        //add_images_adapter.imageView.setImageResource(R.drawable.card);
                        compressImage(fil.getAbsolutePath());
                          f= new File(filename);

                        listone.add(f);
                        if(add_images_adapter==null) {
                            add_images_adapter = new Add_Images_Adapter(this, listone);
                            lv.setAdapter(add_images_adapter);
                        }
                        else
                        {
                         add_images_adapter.notifyDataSetChanged();
                        }



                        //adata();
                        //uploadImage();
//
                    }

                    break;
                case PHOTO_PICKER_ID:
                    if (requestCode == PHOTO_PICKER_ID && resultCode == Activity.RESULT_OK && null != data) {
                        Log.e("Result Ok", "" + data);
                        Uri selectedImage = data.getData();
                        Log.e("selected image", "" + selectedImage);
                        Log.e("selected image", "" + getPath(selectedImage));
                        compressImage(getPath(selectedImage));
                        f = new File(filename);

                        listone.add(f);
                        Log.e("file list",""+listone);
                        if(add_images_adapter==null) {

                            add_images_adapter = new Add_Images_Adapter(this, listone);
                            lv.setAdapter(add_images_adapter);
                        }
                        else
                        {
                            add_images_adapter.notifyDataSetChanged();
                        }
                      //  adata();
                        //uploadImage();

                    }

                    break;
            }
        } catch (Exception e) {
            Log.d("krvrrusbviuritiribtr", e.getMessage());
        }
    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = Add_Post_Images.this.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null)
            return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s = cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public String compressImage(String imageUri) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        filename = getFilename();
        try {
            out = new FileOutputStream(filename);

//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = Add_Post_Images.this.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

//public void adata()
//{
//    if(picdata==1)
//    {
//        if(f!=null) {
//            imageView1.setImageURI(Uri.fromFile(f));
//            imageView2.setVisibility(View.VISIBLE);
//            //radioButton2.setVisibility(View.VISIBLE);
//            tremove2.setVisibility(View.VISIBLE);
//            layout2.setVisibility(View.VISIBLE);
//            f=null;
//        }
//    }
//    if(picdata==2)
//    {
//        if(f!=null) {
//            imageView2.setImageURI(Uri.fromFile(f));
//            imageView3.setVisibility(View.VISIBLE);
//           // radioButton3.setVisibility(View.VISIBLE);
//            tremove3.setVisibility(View.VISIBLE);
//            layout3.setVisibility(View.VISIBLE);
//            f=null;
//        }
//    }
//    if(picdata==3)
//    {
//        if(f!=null) {
//            imageView3.setImageURI(Uri.fromFile(f));
//            imageView4.setVisibility(View.VISIBLE);
//           // radioButton4.setVisibility(View.VISIBLE);
//            tremove4.setVisibility(View.VISIBLE);
//            layout4.setVisibility(View.VISIBLE);
//            f=null;
//        }
//    }
//    if(picdata==4)
//    {
//        if(f!=null) {
//            imageView4.setImageURI(Uri.fromFile(f));
//            imageView5.setVisibility(View.VISIBLE);
//           // radioButton5.setVisibility(View.VISIBLE);
//            tremove5.setVisibility(View.VISIBLE);
//            layout5.setVisibility(View.VISIBLE);
//            f=null;
//        }
//    }
//    if(picdata==5)
//    {
//        if(f!=null) {
//            imageView5.setImageURI(Uri.fromFile(f));
//            imageView6.setVisibility(View.VISIBLE);
//           // radioButton6.setVisibility(View.VISIBLE);
//            tremove6.setVisibility(View.VISIBLE);
//            layout6.setVisibility(View.VISIBLE);
//            f=null;
//        }
//    }
//    if(picdata==6)
//    {
//        if(f!=null) {
//            imageView6.setImageURI(Uri.fromFile(f));
//f=null;
//        }
//    }
//}
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//
//            case R.id.image1:
//                if(f==null) {
//                    dialog();
//                    picdata = 1;
//                }
//                break;
//
////            case R.id.radio1:
////                    radioButton6.setChecked(false);
////                    radioButton2.setChecked(false);
////                    radioButton3.setChecked(false);
////                    radioButton4.setChecked(false);
////                    radioButton5.setChecked(false);
////                    value_text=1;
////                break;
//            case R.id.remove1:
//                if (!(imageView1 ==null)) {
//                    imageView1.setImageResource(R.drawable.plus_button);
//                    f=null;
//                }
//                break;
////=====================
//            case R.id.image2:
//                if(f==null) {
//                    dialog();
//                    picdata = 2;
//                }
//                break;
//
////            case R.id.radio2:
////                    radioButton6.setChecked(false);
////                    radioButton1.setChecked(false);
////                    radioButton3.setChecked(false);
////                    radioButton4.setChecked(false);
////                    radioButton5.setChecked(false);
////                    value_text=1;
////                break;
//            case R.id.remove2:
//                layout2.setVisibility(View.GONE);
//                if (!(imageView2 ==null)) {
//                    imageView2.setImageResource(R.drawable.plus_button);
//                    f=null;
//                }
//                break;
//                //=====================
//
//            case R.id.image3:
//                if(f==null) {
//                    dialog();
//                    picdata = 3;
//                }
//
//                break;
//
////            case R.id.radio3:
////                radioButton1.setChecked(false);
////                radioButton2.setChecked(false);
////                radioButton4.setChecked(false);
////                radioButton5.setChecked(false);
////                radioButton6.setChecked(false);
////                value_text=1;
////                break;
//            case R.id.remove3:
//                layout3.setVisibility(View.GONE);
//                if (!(imageView3 ==null)) {
//                    imageView3.setImageResource(R.drawable.plus_button);
//                    f=null;
//                }
//                break;
//                //===================
//            case R.id.image4:
//                if(f==null) {
//                    dialog();
//                    picdata = 4;
//                }
//
//                break;
//
////            case R.id.radio4:
////                radioButton1.setChecked(false);
////                radioButton2.setChecked(false);
////                radioButton3.setChecked(false);
////                radioButton5.setChecked(false);
////                radioButton6.setChecked(false);
////                value_text=1;
////                break;
//            case R.id.remove4:
//                layout4.setVisibility(View.GONE);
//                if (!(imageView4 ==null)) {
//                    imageView4.setImageResource(R.drawable.plus_button);
//                    f=null;
//                }
//                break;
//                //==================
//            case R.id.image5:
//                if(f==null) {
//                    dialog();
//                    picdata = 5;
//                }
//                break;
//
////            case R.id.radio5:
////                radioButton1.setChecked(false);
////                radioButton2.setChecked(false);
////                radioButton3.setChecked(false);
////                radioButton4.setChecked(false);
////                radioButton6.setChecked(false);
////                value_text=1;
////                break;
//            case R.id.remove5:
//                layout5.setVisibility(View.GONE);
//                if (!(imageView5 ==null)) {
//                    imageView5.setImageResource(R.drawable.plus_button);
//                    f=null;
//                }
//                break;
//                //==================
//            case R.id.image6:
//                if(f==null) {
//                    dialog();
//                    picdata = 6;
//                }
//
//                break;
//
////            case R.id.radio6:
////                   radioButton1.setChecked(false);
////                   radioButton2.setChecked(false);
////                   radioButton3.setChecked(false);
////                   radioButton4.setChecked(false);
////                   radioButton5.setChecked(false);
////                   value_text=1;
////
////                break;
//            case R.id.remove6:
//                layout6.setVisibility(View.GONE);
//                if (!(imageView6 ==null)) {
//                    imageView6.setImageResource(R.drawable.plus_button);
//                    f=null;
//                    break;
//                }
//            default:
//
//        }
//    }
}

//class Add_Images_Adapter extends BaseAdapter {
//    public List buttonlist = new ArrayList();
//    Add_Post_Images add_post_images;
//    public ImageView imageView;
//    private static final int REQUEST_CODE_CAPTURE_IMAGE = 2500;
//    private static final int PHOTO_PICKER_ID = 1;
//    String filename;
//    File f;
//    Uri imageUri;
//    private final Context context;
//
//    public Add_Images_Adapter(Context context, List listpost) {
//        this.context = context;
//        this.buttonlist = listpost;
//    }
//
//    @Override
//    public int getCount() {
//        return buttonlist.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return buttonlist.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return buttonlist.indexOf(buttonlist.get(position));
//    }
//
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        LayoutInflater inflater;
//        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        convertView = inflater.inflate(R.layout.add_post_images_custom, parent, false);
//        TextView tv = (TextView) convertView.findViewById(R.id.remove1);
//        imageView = (ImageView) convertView.findViewById(R.id.image1);
//        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.radio1);
//
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                add_post_images.a++;
//                add_post_images.listpost.add(add_post_images.a);
//
//                add_post_images.dialog();
////                Bitmap myBitmap = BitmapFactory.decodeFile(add_post_images.f.getAbsolutePath());
////                imageView.setImageBitmap(myBitmap);
//
//                imageView.setImageResource(R.drawable.btn_back);
//                Toast.makeText(context, "" + buttonlist.get(position), Toast.LENGTH_SHORT).show();
//                Log.e("value", "" + add_post_images.a);
//                add_post_images.change();
//            }
//        });
//
//
//        return convertView;
//    }
//}
