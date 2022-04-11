package com.sanyasdada.ecommerceapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.activities.DetailedActivity;
import com.sanyasdada.ecommerceapplication.models.NewProductsModel;

import org.w3c.dom.Text;

import java.util.List;

public class NewProdutsAdapter extends RecyclerView.Adapter<NewProdutsAdapter.MyViewHolder> {

    private Context context;
    private List<NewProductsModel> list;

    public NewProdutsAdapter(Context context, List<NewProductsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_products, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        NewProductsModel productModel = list.get(holder.getAdapterPosition());
        Glide.with(context)
                .load(productModel.getImg_url())
                .into(holder.newImg);
        holder.newName.setText(productModel.getName());
        holder.newPrice.setText(String.valueOf(productModel.getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed", productModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView newImg;
        TextView newName, newPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_pro_name);
            newPrice = itemView.findViewById(R.id.new_price);
        }
    }
}
