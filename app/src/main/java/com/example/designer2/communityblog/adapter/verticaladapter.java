package com.example.designer2.communityblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.designer2.communityblog.activites.DetailedPosts;
import com.example.designer2.communityblog.model.ModelCategory;
import com.example.designer2.communityblog.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class verticaladapter extends RecyclerView.Adapter<verticaladapter.ViewHolder> {
    private List<ModelCategory> mData;
    private LayoutInflater mInflater;
MyClickListener myClickListener;
    Context context;
    public verticaladapter(Context context, List<ModelCategory> data,MyClickListener myClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.myClickListener = myClickListener;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.vertical_row_item, viewGroup, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent i  =  new Intent(context,DetailedPosts.class);
               // i.putExtra("name",mData.get(viewHolder.getAdapterPosition()).getTitle());
               // i.putExtra("description",mData.get(viewHolder.getAdapterPosition()).getDescription());
               // i.putExtra("username",mData.get(viewHolder.getAdapterPosition()).getUsername());
               // i.putExtra("category_id",mData.get(viewHolder.getAdapterPosition()).getCategory_id());


               // i.putExtra("date",mData.get(viewHolder.getAdapterPosition()).getCreatedat());
               // i.putExtra("img",mData.get(viewHolder.getAdapterPosition()).getImgUrl());
              // context.startActivity(i);
                ////

            }
        });
       // viewHolder.view_container.setOnClickListener(new View.OnClickListener() {




        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.myTextViewTitle.setText(mData.get(i).getCatName());
        holder.username.setText(mData.get(i).getUsername());
        holder.created_date.setText(mData.get(i).getCreated_date());
        Picasso.with(context).load(mData.get(i).getThumnailUrl()).into(holder.imageView_post);




        holder.onClickRow(i,myClickListener);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setfilter(List<ModelCategory> filtermodel) {
        mData=new ArrayList<>();
        mData.addAll(filtermodel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextViewTitle;
        ImageView imageView_post;
        TextView username;
        TextView created_date;
        LinearLayout containers;


        ViewHolder(View itemView) {
            super(itemView);
            imageView_post = itemView.findViewById(R.id.thumbnail_id);
            myTextViewTitle = itemView.findViewById(R.id.post_list_title);
            username = itemView.findViewById(R.id.author_name);
            created_date = itemView.findViewById(R.id.date_id);
            containers = itemView.findViewById(R.id.container_vertical);


        }
        public void  onClickRow(final int position,final MyClickListener myClickListener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myClickListener.onClick(position);
                    Intent i =  new Intent(context,DetailedPosts.class);
                    i.putExtra("title",mData.get(position).getCatName());
                    i.putExtra("name",mData.get(position).getUsername());
                    i.putExtra("url",mData.get(position).getThumnailUrl());
                    i.putExtra("created_date",mData.get(position).getCreated_date());
                    i.putExtra("descriptio",mData.get(position).getDescriptio());
                    context.startActivity(i);


                }
            });

        }


    }
    public interface MyClickListener{
        public void onClick(int position);
    }
}
