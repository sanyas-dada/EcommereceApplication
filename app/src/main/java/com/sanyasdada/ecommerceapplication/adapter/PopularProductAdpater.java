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
import com.sanyasdada.ecommerceapplication.activities.DetailedActivity;
import com.sanyasdada.ecommerceapplication.models.PopularProductModel;

import java.util.List;

public class PopularProductAdpater extends RecyclerView.Adapter<PopularProductAdpater.MyViewModel> {

    private Context context;
    private List<PopularProductModel> list;

    public PopularProductAdpater(Context context, List<PopularProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.popular_product_items, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        PopularProductModel productModel = list.get(holder.getAdapterPosition());
        Glide.with(context).load(productModel.getImg_url()).into(holder.allImg);
        holder.allProduct.setText(productModel.getName());
        holder.allPrice.setText(String.valueOf(productModel.getPrice()));

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

    public class MyViewModel extends RecyclerView.ViewHolder {
        ImageView allImg;
        TextView allProduct, allPrice;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            allImg = itemView.findViewById(R.id.all_img);
            allProduct = itemView.findViewById(R.id.all_product);
            allPrice = itemView.findViewById(R.id.all_price);
        }
    }
}
