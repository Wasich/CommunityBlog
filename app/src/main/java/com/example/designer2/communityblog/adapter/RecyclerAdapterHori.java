package com.example.designer2.communityblog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.designer2.communityblog.model.ModelCategory;
import com.example.designer2.communityblog.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapterHori extends RecyclerView.Adapter<RecyclerAdapterHori.ViewHolder> {
    private List<ModelCategory> mData;
    private LayoutInflater mInflater;
    MyClickListener myClickListener;
    Context context;

    public RecyclerAdapterHori(Context context, List<ModelCategory> data,MyClickListener myClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.myClickListener = myClickListener;
        this.context = context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.horizontal_row_item, viewGroup, false);
        View view1 = mInflater.inflate(R.layout.vertical_row_item,viewGroup,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        holder.myTextViewTitle.setText(mData.get(i).getCatName());
        Picasso.with(context).load(mData.get(i).getThumnailUrl()).into(holder.imageView_product);
        holder.onClickRow(i,myClickListener);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView myTextViewTitle;
        ImageView imageView_product;
        RelativeLayout container;


        ViewHolder(View itemView) {
            super(itemView);
            imageView_product = itemView.findViewById(R.id.image_view);
            myTextViewTitle = itemView.findViewById(R.id.name);
            container = itemView.findViewById(R.id.horizontal_container);


        }
        public void  onClickRow(final int position,final MyClickListener myClickListener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myClickListener.onClick(position);



                }
            });

        }


    }
    public interface MyClickListener{
        public void onClick(int position);
    }
}
