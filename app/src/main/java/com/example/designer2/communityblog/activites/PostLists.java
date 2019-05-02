package com.example.designer2.communityblog.activites;
import com.example.designer2.communityblog.Registeration;
import com.example.designer2.communityblog.SignIn;
import com.example.designer2.communityblog.SignUp;
import com.example.designer2.communityblog.adapter.*;
import com.example.designer2.communityblog.model.ModelCategory;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class PostLists extends AppCompatActivity {

    RecyclerView recyclerViewVr;
    RecyclerView recyclerViewHori;
    verticaladapter adapterVr;
    RecyclerAdapterHori adapterHori;
    ArrayList<ModelCategory> list = new ArrayList<>();
    ArrayList<ModelCategory> listHori = new ArrayList<>();
    FloatingActionButton floatingActionButton;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_lists);
        Integer id = getIntent().getIntExtra("id",2);
       // Toast.makeText(this,String.valueOf( id), Toast.LENGTH_SHORT).show();


        //floating action button

        floatingActionButton = findViewById(R.id.floting_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostLists.this,CreatePost.class));

            }
        });

        verticalServerCall(id);

        horiServerCall();
        // action bar name
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post List");



    }
    private void horiServerCall() {
        recyclerViewHori = findViewById(R.id.horizontal_rec);
        recyclerViewHori.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapterHori = new RecyclerAdapterHori(this, listHori, new RecyclerAdapterHori.MyClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(PostLists.this, "loading please wait...", Toast.LENGTH_SHORT).show();
                verticalServerCall(listHori.get(position).getId());
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
                        listHori.add(new ModelCategory(url,name,name,name,name,name,Integer.parseInt(id)));
                        if(adapterHori!=null)
                            adapterHori.notifyDataSetChanged();
                    }
                    if(adapterHori==null)

                        adapterHori.notifyDataSetChanged();


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
        recyclerViewHori.setAdapter(adapterHori);
    }
    private void verticalServerCall(final int id) {
        list.clear();
        recyclerViewVr = findViewById(R.id.recyclerview_id_vertical);
        recyclerViewVr.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapterVr = new verticaladapter(this, list, new verticaladapter.MyClickListener() {
            @Override
            public void onClick(int position) {


                ////
               // Toast.makeText(PostLists.this, "done your job", Toast.LENGTH_SHORT).show();
            }
        });

        final String url = "http://mufassirislam.com/apps/blog/api/post.php?action=getPostListById&category_id="+id;
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
                        String title = json.getString("post_title");
                        String name = json.getString("username");

                        String url = json.getString("url");
                        String created_date = json.getString("created_at");
                        String descriptio = json.getString("post_description");
                        String caption = json.getString("caption");


//                        Toast.makeText(Meme.this, name, Toast.LENGTH_SHORT).show();
                        list.add(new ModelCategory(url,title,name,created_date,descriptio,caption,Integer.parseInt(id)));
                        if(adapterVr!=null)
                            adapterVr.notifyDataSetChanged();
                    }
                    if(adapterVr==null)
                        verticalServerCall(3);

                        adapterVr.notifyDataSetChanged();


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
        recyclerViewVr.setAdapter(adapterVr);
    }





    // Search View




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        final MenuItem menuItem=menu.findItem(R.id.search);

        searchView=(SearchView) menuItem.getActionView();
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).
                setTextColor(Color.parseColor("#FFFFFF"));
        ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).
                setHintTextColor(Color.parseColor("#FFFFFF"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified())
                {
                    searchView.setIconified(true);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                final List<ModelCategory> filtermodel=filter(list,newText);
                adapterVr.setfilter(filtermodel);
                return true;
            }
        });
        return true;
    }
    private List<ModelCategory> filter(List<ModelCategory> pl, String query)
    {
        query=query.toLowerCase();
        final List<ModelCategory>filtermodel=new ArrayList<>();
        for (ModelCategory model:pl)
        {
            String text=model.getCatName().toLowerCase();
            if (text.startsWith(query) )
            {
                filtermodel.add(model);

            }
        }
        return filtermodel;
    }

}
