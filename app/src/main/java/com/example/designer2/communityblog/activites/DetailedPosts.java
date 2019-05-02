package com.example.designer2.communityblog.activites;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.designer2.communityblog.R;
import com.squareup.picasso.Picasso;

public class DetailedPosts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_posts);
        getSupportActionBar().hide();
        // receive data from jason server
        String title = getIntent().getExtras().getString("title");
        String descriptio = getIntent().getExtras().getString("descriptio");
       // String categoryname = getIntent().getExtras().getString("category");
        String name = getIntent().getExtras().getString("name");

        String created_date = getIntent().getExtras().getString("created_date");
        String url = getIntent().getExtras().getString("url");


        //views iniatally

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.colap_tool_bar_layout);
        collapsingToolbarLayout.setTitleEnabled(true);


        TextView tv_name = findViewById(R.id.aa_title_name);
        TextView tv_date = findViewById(R.id.aa_date_id);
        TextView tv_description = findViewById(R.id.aa_description);
        TextView tv_user = findViewById(R.id.aa_auther_name);
        ImageView img = findViewById(R.id.aa_thumbnail_id);


        // setting value to  each on postition
        tv_name.setText(title);
        tv_date.setText(created_date);
        tv_description.setText(descriptio);
        tv_user.setText(name);


        collapsingToolbarLayout.setTitle(title);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loadingshape).error(R.drawable.loadingshape);
        Glide.with(this).load(url).apply(requestOptions).into(img);







    }

    }

