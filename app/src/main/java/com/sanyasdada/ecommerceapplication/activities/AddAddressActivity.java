package com.sanyasdada.ecommerceapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanyasdada.ecommerceapplication.R;

import java.util.HashMap;
import java.util.Map;

public class AddAddressActivity extends AppCompatActivity {

    EditText name,address,city,postalCode,phoneNumber;
    Toolbar toolbar;
    Button addAddressBtn;

    FirebaseAuth auth;
    FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);


        toolbar =findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // back pressed Toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();

        name =findViewById(R.id.name);
        address =findViewById(R.id.address_lane);
        city =findViewById(R.id.city);
        postalCode =findViewById(R.id.postal_code);
        phoneNumber =findViewById(R.id.phone_number);
        addAddressBtn =findViewById(R.id.add_address);


        addAddressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = name.getText().toString();
                String userAddress = address.getText().toString();
                String userCity = city.getText().toString();
                String userPostalCode = postalCode.getText().toString();
                String userPhoneNumber = phoneNumber.getText().toString();

                String final_address = "";
                if(!userName.isEmpty()){
                    final_address+=userName;
                }
                if(!userAddress.isEmpty()){
                    final_address+=userAddress;
                }
                if(!userCity.isEmpty()){
                    final_address+=userCity;
                }
                if(!userPostalCode.isEmpty()){
                    final_address+=userPostalCode;
                }
                if(!userPhoneNumber.isEmpty()){
                    final_address+=userPhoneNumber;
                }
                if(!userName.isEmpty() && !userAddress.isEmpty() &&!userCity.isEmpty() &&!userPostalCode.isEmpty() &&!userPhoneNumber.isEmpty()){
                    Map<String,String> map = new HashMap<>();
                    map.put("userAddress",final_address);



                    fireStore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                            .collection("Address").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(AddAddressActivity.this,"Address Added",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddAddressActivity.this,DetailedActivity.class));
                                finish();
                            }
                        }
                    });
                }else{
                    Toast.makeText(AddAddressActivity.this, "Kindly Fill All Field", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}