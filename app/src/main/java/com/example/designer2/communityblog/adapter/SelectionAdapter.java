package com.example.designer2.communityblog.adapter;
import com.example.designer2.communityblog.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.designer2.communityblog.R;
import com.example.designer2.communityblog.model.ModelCategory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.designer2.communityblog.R.color.colorAccent;

public class SelectionAdapter extends RecyclerView.Adapter<SelectionAdapter.ViewHolder> {


    private List<ModelCategory> mData;
    private LayoutInflater mInflater;
    MyClickListener myClickListener;
    Context context;
    private List<Integer> selectedIds = new ArrayList<>();
    public SelectionAdapter(Context context, List<ModelCategory> data,MyClickListener myClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.myClickListener = myClickListener;
        this.context = context;
    }
    @NonNull
    @Override
    public SelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.selection_row_item, viewGroup, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectionAdapter.ViewHolder holder, final int i) {
        holder.TV_NAme.setText(mData.get(i).getCatName());
        int id = mData.get(i).getId();
        if (selectedIds.contains(id)){
            holder.containerss.setBackground(new ColorDrawable(ContextCompat.getColor(context,R.color.colorPrimary)));

            }
            else {
                //else remove selected item color.
                holder.containerss.setBackground(new ColorDrawable(ContextCompat.getColor(context,android.R.color.white)));
        }




       // Picasso.with(context).load(mData.get(i).getThumnailUrl()).into(holder.imageView_category);
        holder.onClickRow(i,myClickListener);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public ModelCategory getItem(int position){
        return mData.get(position);
    }

    public void setSelectedIds(List<Integer> selectedIds) {
        this.selectedIds = selectedIds;
        notifyDataSetChanged();
    }

    public void setfilter(List<ModelCategory> filtermodel) {
        mData=new ArrayList<>();
        mData.addAll(filtermodel);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        TextView TV_NAme;
       //ImageView imageView_category;
        CardView containerss;


        ViewHolder(View itemView) {
            super(itemView);
            TV_NAme = itemView.findViewById(R.id.selecion_title_it);
         //   imageView_category = itemView.findViewById(R.id.selectio_image);
            containerss = itemView.findViewById(R.id.selection_card);


        }
        public void  onClickRow (final int position,final SelectionAdapter.MyClickListener myClickListener)
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
    public interface MyClickListener {
        public void onClick(int position);
    }
}
