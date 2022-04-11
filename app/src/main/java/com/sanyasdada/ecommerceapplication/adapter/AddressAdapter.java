package com.sanyasdada.ecommerceapplication.adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.models.AddressModel;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewModel> {

    private List<AddressModel> list;
    private Context context;
    SelectedAddress selectedAddress;
    private RadioButton selectedRadioBtn;

    public AddressAdapter(List<AddressModel> list, Context context,SelectedAddress selectedAddress) {
        this.list = list;
        this.context = context;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.address_items, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewModel holder, int position) {
        AddressModel addressModel = list.get(holder.getAdapterPosition());
        holder.address.setText(addressModel.getUserAddress());
        holder.radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(AddressModel address : list){
                    address.setSelected(false);
                }
                list.get(holder.getAdapterPosition()).setSelected(true);

                if(selectedRadioBtn !=null){
                    selectedRadioBtn.setChecked(true);
                }
                selectedRadioBtn = (RadioButton) view;
                selectedRadioBtn.setChecked(true);
                selectedAddress.setAddress(addressModel.getUserAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewModel extends RecyclerView.ViewHolder {

        TextView address;
        RadioButton radioButton;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.address_add);
            radioButton = itemView.findViewById(R.id.select_address);
        }
    }
    public interface SelectedAddress {
        void setAddress(String address);
    }
}
