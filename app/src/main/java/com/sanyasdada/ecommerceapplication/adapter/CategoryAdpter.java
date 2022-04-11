package com.sanyasdada.ecommerceapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.activities.ShowAllActivity;
import com.sanyasdada.ecommerceapplication.models.CategoryModel;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdpter extends RecyclerView.Adapter<CategoryAdpter.MyViewHolder> {

    private Context context;
    private List<CategoryModel> list;

    public CategoryAdpter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CategoryModel model=list.get(position);
        Glide.with(context)
                .load(list.get(position).getImg_url())
                .into(holder.catImage);
        holder.catName.setText(list.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ShowAllActivity.class);
                intent.putExtra("type",model.getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        ImageView catImage;
        TextView catName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
          catImage=  itemView.findViewById(R.id.cat_image);
          catName=  itemView.findViewById(R.id.cat_name);
        }
    }
}
