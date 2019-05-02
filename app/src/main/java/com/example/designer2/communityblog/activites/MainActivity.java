package com.example.designer2.communityblog.activites;
import com.example.designer2.communityblog.adapter.*;
import com.example.designer2.communityblog.model.*;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.designer2.communityblog.R;


import org.json.JSONArray;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {



    private RecyclerView gridrecyclerview;
    GridrecyclerAdapter gridrecyclerAdapter;
    private List<ModelCategory> firscategory;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firscategory = new ArrayList<>();
        gridrecyclerview = findViewById(R.id.gridrecyclerview);

        catServerCall();








    }
    private void catServerCall() {
        gridrecyclerview = findViewById(R.id.gridrecyclerview);
        gridrecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        gridrecyclerAdapter = new GridrecyclerAdapter(this, firscategory, new GridrecyclerAdapter.MyClickListener() {
            @Override
            public void onClick(int position) {
//                Toast.makeText(MainActivity.this, String.valueOf(firscategory.get(position).getId()), Toast.LENGTH_SHORT).show();
                Intent i =  new Intent(getApplicationContext(),PostLists.class);
                i.putExtra("id",firscategory.get(position).getId());
                    startActivity(i);

              //  verticalServerCall(firscategory.get(position).getId());
            }
        });

        final String url = "https://mufassirislam.com/apps/blog/api/category.php?action=getCategoryList";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject object1 = new JSONObject(response);
                    JSONArray object2 = object1.getJSONArray("result");
                    for (int i = 0; i<= object2.length();i++) {
                        JSONObject json = object2.getJSONObject(i);
                        String id = json.getString("category_id");
                        String name = json.getString("category_name");
                        String url = json.getString("thumb");

//                        Toast.makeText(Meme.this, name, Toast.LENGTH_SHORT).show();
                        firscategory.add(new ModelCategory(url,name,name,name,name,name,Integer.parseInt(id)));
                        if(gridrecyclerAdapter!=null)
                            gridrecyclerAdapter.notifyDataSetChanged();
                    }
                    if(gridrecyclerAdapter!=null)
                        gridrecyclerAdapter.notifyDataSetChanged();


                }catch (Exception e){
//                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        ///////////////////
        gridrecyclerview.setAdapter(gridrecyclerAdapter);
    }





}
