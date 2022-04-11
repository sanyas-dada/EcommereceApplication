package com.sanyasdada.ecommerceapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.models.MyCartModel;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyViewHolder> {
    private List<MyCartModel> list;
    private Context context;
    int totalAmount=0;

    public MyCartAdapter(List<MyCartModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyCartModel myCartModel = list.get(holder.getAdapterPosition());
        holder.date.setText(myCartModel.getCurrentDate());
        holder.time.setText(myCartModel.getCurrentTime());
        holder.name.setText(myCartModel.getProductName());
        holder.price.setText(myCartModel.getProductPrice());
        holder.totalPrice.setText(String.valueOf(myCartModel.getProductPrice()));
        holder.totalQuantity.setText(myCartModel.getTotalQuantity());

        // Total amount pass to cart Activity
        totalAmount = totalAmount + myCartModel.getTotalPrice();
        Intent  intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date, time, name, price, totalPrice, totalQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);

        }
    }
}
