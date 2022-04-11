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
import com.sanyasdada.ecommerceapplication.models.ShowAllModel;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.MyViewModel>{

    private Context context;
    private List<ShowAllModel> list;

    public ShowAllAdapter(Context context, List<ShowAllModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view =  LayoutInflater.from(context).inflate(R.layout.show_all_items,parent,false);
      return  new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        ShowAllModel allModel = list.get(holder.getAdapterPosition());
        Glide.with(context)
                .load(allModel.getImg_url())
                .centerCrop()
                .into(holder.imageView);
        holder.itemCost.setText("Rs.$"+String.valueOf(allModel.getPrice()));
        holder.itemName.setText(allModel.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedActivity.class);
                intent.putExtra("detailed",allModel);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView itemCost,itemName;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);
           imageView= itemView.findViewById(R.id.img_of_all);
            itemCost =itemView.findViewById(R.id.item_cost);
           itemName =itemView.findViewById(R.id.item_name);
        }
    }
}
