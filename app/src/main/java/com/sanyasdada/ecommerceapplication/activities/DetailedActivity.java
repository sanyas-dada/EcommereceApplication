package com.sanyasdada.ecommerceapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.models.NewProductsModel;
import com.sanyasdada.ecommerceapplication.models.PopularProductModel;
import com.sanyasdada.ecommerceapplication.models.ShowAllModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg;
    TextView rating, name, description, price,quantity;
    Button addToCart, buyNow;
    ImageView addItem, removeItem;
    Toolbar toolbar;
    FirebaseAuth auth;

    //New products
    NewProductsModel newProductsModel = null;

    //Popular Product
    PopularProductModel popularproductModel = null;

    //Detailed Product
    ShowAllModel showAllModel = null;

    private FirebaseFirestore firestore;

    int totalQuantity;
    int totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        toolbar = findViewById(R.id.detailed_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // back pressed on toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        //getSupportActionBar().hide();
        // hide the action bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

          auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        final Object obj = getIntent().getSerializableExtra("detailed");

        if (obj instanceof NewProductsModel) {
            newProductsModel = (NewProductsModel) obj;

        } else if (obj instanceof PopularProductModel) {
            popularproductModel = (PopularProductModel) obj;
        } else if (obj instanceof ShowAllModel) {
            showAllModel = (ShowAllModel) obj;
        }

        detailedImg = findViewById(R.id.detailed_image);
        name = findViewById(R.id.detailed_name);
        rating = findViewById(R.id.rating);
        description = findViewById(R.id.detailed_description_product);
        price = findViewById(R.id.detailed_price_of_product);

        addItem = findViewById(R.id.add_item_product);
        quantity = findViewById(R.id.cont_number);
        removeItem = findViewById(R.id.remove_item_product);


        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now_start);

        // NewProducts
        if (newProductsModel != null) {
            Glide.with(getApplicationContext()).load(newProductsModel.getImg_url()).into(detailedImg);
            name.setText(newProductsModel.getName());
            rating.setText(newProductsModel.getRating());
            description.setText(newProductsModel.getDescription());
            price.setText(String.valueOf(newProductsModel.getPrice()));
            totalPrice = newProductsModel.getPrice() * totalQuantity;
        }

        //Popular Products
        if (popularproductModel != null) {
            Glide.with(getApplicationContext()).load(popularproductModel.getImg_url()).into(detailedImg);
            name.setText(popularproductModel.getName());
            rating.setText(popularproductModel.getRating());
            description.setText(popularproductModel.getDescription());
            price.setText(String.valueOf(popularproductModel.getPrice()));

            totalPrice = popularproductModel.getPrice() * totalQuantity;

        }

        // ShowAll Products
        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg_url()).into(detailedImg);
            name.setText(showAllModel.getName());
            rating.setText(showAllModel.getRating());
            description.setText(showAllModel.getDescription());
            price.setText(String.valueOf(showAllModel.getPrice()));

            totalPrice = showAllModel.getPrice() * totalQuantity;

        }


        // Buy Now

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailedActivity.this,AddressActivity.class));
            }
        });
        // Add to Cart
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addtoCart();
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity <5){
                    totalQuantity++;
                    quantity.setText(String.valueOf(totalQuantity));
                    if(newProductsModel != null){
                        totalPrice = newProductsModel.getPrice() * totalQuantity;
                    }
                    if(popularproductModel != null){
                        totalPrice = popularproductModel.getPrice() * totalQuantity;
                    }
                    if(showAllModel != null){
                        totalPrice = showAllModel.getPrice() * totalQuantity;
                    }

                }

            }
        });
        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(totalQuantity >1){
                    totalQuantity--;
                    quantity.setText(String.valueOf(totalQuantity));

                }
            }
        });


    }

    private void addtoCart() {

        String  saveCurrentTime,saveCurrentDate;


        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("productName",name.getText().toString());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalPrice);


        firestore.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("User").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this, "Added To A Cart Successfully", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

    }
}