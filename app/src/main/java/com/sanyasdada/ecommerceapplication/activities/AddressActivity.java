package com.sanyasdada.ecommerceapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.adapter.AddressAdapter;
import com.sanyasdada.ecommerceapplication.models.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends AppCompatActivity implements AddressAdapter.SelectedAddress {

    Button addAddress;
    RecyclerView recyclerView;
    private List<AddressModel> addressModelList;
    private AddressAdapter addressAdapter;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    Button  paymentButton;
    Toolbar toolbar;
    String  mAddress ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

       toolbar = findViewById(R.id.address_toolbar);
       setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

          // Back Pressed on Toolbar
       toolbar.setNavigationOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               finish();
           }
       });


       auth = FirebaseAuth.getInstance();
       fireStore = FirebaseFirestore.getInstance();

       addAddress = findViewById(R.id.add_address_activity);
       paymentButton =findViewById(R.id.button_payment);
       recyclerView =findViewById(R.id.address_recycler);


       recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
       addressModelList = new ArrayList<>();
       addressAdapter = new AddressAdapter(addressModelList,getApplicationContext(),this);
       recyclerView.setAdapter(addressAdapter);


        fireStore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("Address").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot doc : task.getResult().getDocuments()) {
                        AddressModel addressModel = doc.toObject(AddressModel.class);
                        addressModelList.add(addressModel);
                        addressAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(AddressActivity.this, "data isn't loaded from firebase successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

       paymentButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(AddressActivity.this,PaymentActivity.class));
           }
       });


       addAddress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(AddressActivity.this,AddAddressActivity.class));
           }
       });
    }

    @Override
    public void setAddress(String address) {
        mAddress = address;

    }
}