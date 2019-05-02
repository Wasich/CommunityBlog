package com.example.designer2.communityblog.activites;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.example.designer2.communityblog.R;
import com.example.designer2.communityblog.Registeration;
import com.example.designer2.communityblog.SelectCategory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreatePost extends AppCompatActivity implements View.OnClickListener {
    EditText title, caption, description, username, email;
    Button post, chooseimgbtn,choosecategorybtn;
    ImageView imageupload;
    ProgressDialog progressDialog;
    Boolean Val = true;
    private final int IMG_REQUEST = 1;
    private Bitmap bitmap;

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "MyPrefs" ;
   public static final String Title = "Titlekey";
   public static final String Short = "ShortKey";
   public static final String Content = "ContentKey";




    public static final String URL = "http://mufassirislam.com/apps/";
    public static final String ROOT_URL = URL + "blog/api/";
    //public static final String URL_ADD = ROOT_URL + "post.php?action=insertPost";

    public static final String URL_ADD = "http://mufassirislam.com/apps/blog/api/post.php?action=insertPost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);



        username = findViewById(R.id.full_name_form);
        email = findViewById(R.id.email_form);
        description = findViewById(R.id.post_content_form);
        caption = findViewById(R.id.short_description_form);
        title = findViewById(R.id.post_title_form);
        post = findViewById(R.id.submit_form_button);
        chooseimgbtn = findViewById(R.id.choose_image_from_gallery);
        imageupload = findViewById(R.id.upload_image_view);
        choosecategorybtn = findViewById(R.id.select_category);

        sharedPreferences = getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        StringBuilder str = new StringBuilder();
        if (sharedPreferences.contains(Title)) {
            title.setText(sharedPreferences.getString(Title, ""));

        }
        if (sharedPreferences.contains(Short)){
            caption.setText(sharedPreferences.getString(Short,""));
        }
        if (sharedPreferences.contains(Content)){
            description.setText(sharedPreferences.getString(Content,""));
        }
       // if (getIntent().hasExtra("name")){

          //  String name = getIntent().getExtras().getString("name");
           // TextView  tvcat = findViewById(R.id.text_cat);
          //  tvcat.setText(name);
           // Toast.makeText(this,String.valueOf( name), Toast.LENGTH_SHORT).show();
       // }









        choosecategorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = title.getText().toString();

                String shortdes = caption.getText().toString();
                String PostDes = description.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(Title,name);
                editor.putString(Short,shortdes);
                editor.putString(Content,PostDes);

                editor.apply();
               startActivity(new Intent(CreatePost.this,SelectCategory.class));



            }
        });








        chooseimgbtn.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Post");
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();



            }
        });

    }


    public void registerUser()  {
        final String uName = username.getText().toString().trim();
        final String eMail = email.getText().toString().trim();
       // final String Status = status.toString();
        final String Caption = caption.getText().toString().trim();
        final String Title = title.getText().toString().trim();
        final String Des = description.getText().toString().trim();
       // final String Text = tvcat.getText().toString().trim();

        //final int[] id = getIntent().getIntArrayExtra("id");



      // final int id = getIntent().getIntExtra("id",0);


        if (TextUtils.isEmpty(uName)) {
            username.setError("User NAme Fill Must");
            Val = false;
        } else {
            Val = true;
            if (TextUtils.isEmpty(eMail)) {
                email.setError("Email must Fill");
                Val = false;
            } else {
                Val = true;
                if (TextUtils.isEmpty(Caption)) {
                    caption.setError("Caption must fill");
                    Val = false;
                } else {
                    Val = true;
                    if (TextUtils.isEmpty(Title)) {
                        title.setError("Title must fill");
                        Val = false;
                    } else {
                        Val = true;
                        if (TextUtils.isEmpty(Des)) {
                            description.setError("Description must fill");
                            Val = false;
                        } else {
                            Val = true;
                        }
                    }

                }
            }
        }


        if (Val) {
            progressDialog.setMessage("Posting Please Wait....");
            progressDialog.show();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_ADD,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            progressDialog.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Error is :=-", "volly error" + error);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("post_title", Title);
                    params.put("caption", Caption);
                    params.put("post_description", Des);
                    params.put("username", uName);
                    params.put("email", eMail);
                    params.put("url",imageToString(bitmap));

                    params.put("category_id", getIntent().getExtras().getString("id").replaceAll(",$",""));

                    //params.put("category_id",getIntent().getExtras().getString("singleid"));

                   // params.put("category_id",Nws);
                  //  params.put("status",Status);




                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.choose_image_from_gallery:
                selectImage();
                break;


            case R.id.submit_form_button:
                // upLoadImage();
                break;
        }

    }

    private void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IMG_REQUEST && resultCode ==RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageupload.setImageBitmap(bitmap);
                imageupload.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private String imageToString(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgbytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgbytes,Base64.DEFAULT);

    }
}
