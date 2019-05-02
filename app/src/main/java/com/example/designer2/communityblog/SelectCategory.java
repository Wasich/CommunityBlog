package com.example.designer2.communityblog;


import android.content.Intent;
import android.graphics.Color;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
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

import com.example.designer2.communityblog.adapter.SelectionAdapter;
import com.example.designer2.communityblog.adapter.SelectionRecyclerItemClickListener;
import com.example.designer2.communityblog.model.ModelCategory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class SelectCategory extends AppCompatActivity implements ActionMode.Callback {
    private ActionMode actionMode;
    private boolean isMultiSelect = false;
    private List<Integer> selectedIds = new ArrayList<>();
    private RecyclerView selecionrecyclerview;
    SelectionAdapter selectionAdapter;
    private List<ModelCategory> modelCategories;
    SearchView searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);
        modelCategories = new ArrayList<>();
        selecionrecyclerview = findViewById(R.id.select_category_Rv);
        catServerCall();


// action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Choose Category Here");
        selecionrecyclerview.addOnItemTouchListener(new SelectionRecyclerItemClickListener(this, selecionrecyclerview, new SelectionRecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect){

                    multiSelect(position);

                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect){
                    selectedIds = new ArrayList<>();
                    isMultiSelect = true;


                    if (actionMode==null){
                        actionMode = startActionMode(SelectCategory.this);

                    }
                }
                multiSelect(position);

            }
        }));


    }
    private void multiSelect(int position ) {
        ModelCategory data = selectionAdapter.getItem(position);

        if (data != null){
            if (actionMode != null) {
                if (selectedIds.contains(data.getId()))
                    selectedIds.remove(Integer.valueOf(data.getId()));
                else
                    selectedIds.add(data.getId());

                if (selectedIds.size() > 0)

                    actionMode.setTitle("Total Categories    "+String.valueOf(selectedIds.size())); //show selected item count on action mode.

                else{
                    actionMode.setTitle(""); //remove item count from action mode.
                    actionMode.finish(); //hide action mode.
                }
                selectionAdapter.setSelectedIds(selectedIds);

            }
        }
    }

    private void catServerCall() {
        selecionrecyclerview = findViewById(R.id.select_category_Rv);
        selecionrecyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        selectionAdapter = new SelectionAdapter(this, modelCategories, new SelectionAdapter.MyClickListener() {
            @Override
            public void onClick(int position) {
               // Toast.makeText(SelectCategory.this,String.valueOf(modelCategories.get(position).getId()),Toast.LENGTH_SHORT).show();
                //Intent i =  new Intent(getApplicationContext(),com.example.designer2.communityblog.activites.CreatePost.class);
               // i.putExtra("singleid",modelCategories.get(position).getId());
                //i.putExtra("name",modelCategories.get(position).getCatName());
               // startActivity(i);

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
                       // String url = json.getString("thumb");

//                        Toast.makeText(Meme.this, name, Toast.LENGTH_SHORT).show();
                        modelCategories.add(new ModelCategory(url,name,name,name,name,name,Integer.parseInt(id)));
                        if(selectionAdapter!=null)
                            selectionAdapter.notifyDataSetChanged();
                    }
                    if(selectionAdapter!=null)
                        selectionAdapter.notifyDataSetChanged();


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
        selecionrecyclerview.setAdapter(selectionAdapter);
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


                final List<ModelCategory> filtermodel=filter(modelCategories,newText);
                selectionAdapter.setfilter(filtermodel);
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
// multi select action mode
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.menu_select,menu);
        return true;

    }

    @Override
    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.action_add:
                StringBuilder stringBuilder = new StringBuilder();

                for (ModelCategory data:modelCategories){
                    if (selectedIds.contains(data.getId()))
                        stringBuilder.append(data.getId()).append(",");
                    stringBuilder.setLength(stringBuilder.length());




                }
                Intent i =  new Intent(getApplicationContext(),com.example.designer2.communityblog.activites.CreatePost.class);
                i.putExtra("id",String.valueOf(stringBuilder));
               // i.putExtra("name",String.valueOf(stringBuilder1));
                //i.putExtra("id",modelCategories.get(position).getId());
               // i.putExtra("id",modelCategories.get(stringBuilder).getId());
               // i.putExtra("name",modelCategories.get(stringBuilder).getCatName());
                startActivity(i);
                //Toast.makeText(SelectCategory.this,String.valueOf(stringBuilder1),Toast.LENGTH_SHORT).show();
               // Toast.makeText(SelectCategory.this,String.valueOf(modelCategories.get(position).getId()),Toast.LENGTH_SHORT).show();
               // Toast.makeText(this, "Selected items are :" + stringBuilder.toString(), Toast.LENGTH_SHORT).show();
                return true;
        }

        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode =null;
        isMultiSelect = false;
        selectedIds = new ArrayList<>();
        selectionAdapter.setSelectedIds(new ArrayList<Integer>());

    }
}
