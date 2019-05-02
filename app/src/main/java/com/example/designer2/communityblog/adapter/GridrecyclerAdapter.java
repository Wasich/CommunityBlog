package com.example.designer2.communityblog.adapter;

import android.content.Context;

import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.designer2.communityblog.model.ModelCategory;
import com.example.designer2.communityblog.activites.PostLists;
import com.example.designer2.communityblog.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridrecyclerAdapter extends RecyclerView.Adapter<GridrecyclerAdapter.ViewHolder> {
    private List<ModelCategory> mData;
    private LayoutInflater mInflater;
    MyClickListener myClickListener;
    Context context;
    public GridrecyclerAdapter(Context context, List<ModelCategory> data,MyClickListener myClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.myClickListener = myClickListener;
        this.context = context;
    }
    @NonNull
    @Override
    public GridrecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.grid_recyclerview_item, viewGroup, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GridrecyclerAdapter.ViewHolder holder, final int i) {
        holder.myTextViewTitle.setText(mData.get(i).getCatName());

        Picasso.with(context).load(mData.get(i).getThumnailUrl()).into(holder.imageView_category);
        holder.onClickRow(i,myClickListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextViewTitle;
        ImageView imageView_category;
        CardView containers;


        ViewHolder(View itemView) {
            super(itemView);
            imageView_category = itemView.findViewById(R.id.category_img_id);
            myTextViewTitle = itemView.findViewById(R.id.category_title_id);
            containers = itemView.findViewById(R.id.grid_card);


        }
        public void  onClickRow(final int position,final MyClickListener myClickListener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myClickListener.onClick(position);

//                    Intent i =  new Intent(context,PostLists.class);
//                    context.startActivity(i);
                }
            });

        }


    }
    public interface MyClickListener{
        public void onClick(int position);
    }
}
