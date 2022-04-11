package com.sanyasdada.ecommerceapplication.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sanyasdada.ecommerceapplication.R;
import com.sanyasdada.ecommerceapplication.activities.ShowAllActivity;
import com.sanyasdada.ecommerceapplication.adapter.CategoryAdpter;
import com.sanyasdada.ecommerceapplication.adapter.NewProdutsAdapter;
import com.sanyasdada.ecommerceapplication.adapter.PopularProductAdpater;
import com.sanyasdada.ecommerceapplication.models.CategoryModel;
import com.sanyasdada.ecommerceapplication.models.NewProductsModel;
import com.sanyasdada.ecommerceapplication.models.PopularProductModel;

import java.util.ArrayList;
import java.util.List;

/*
 */
public class HomeFragment extends Fragment {


    TextView catShowAll, popularShowAll, newProductShowAll;


    ProgressDialog progressDialog;

    LinearLayout linearLayout;

    // Category recylcerview
    RecyclerView catRecyclerView, productRecyclerView, popularRecyclerView;

    //Category RecyclerView
    CategoryAdpter categoryAdpter;
    List<CategoryModel> categoryModelList;

    // New Products RecyclerView
    NewProdutsAdapter newProdutsAdapter;
    List<NewProductsModel> newProductsModelList;

    //Popular Products RecyclerView
    PopularProductAdpater popularProductAdpater;
    List<PopularProductModel> popularProductModelsList;


    //FireStore
    FirebaseFirestore db;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);



        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(getActivity());

        popularRecyclerView = root.findViewById(R.id.popular_rec);
        productRecyclerView = root.findViewById(R.id.new_product_rec);
        catRecyclerView = root.findViewById(R.id.rec_category);

        newProductShowAll = root.findViewById(R.id.newProducts_see_all);
        catShowAll = root.findViewById(R.id.category_see_all);
        popularShowAll = root.findViewById(R.id.popular_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        newProductShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        popularShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        ImageSlider imageSlider = root.findViewById(R.id.image_slider_github);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider, "IMac For You", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.noon2slider, "Apple", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.daraz, "Samsung", ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slidersamsung, "Realme", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels);


        progressDialog.setTitle("Welcome to My Ecommerce App");
        progressDialog.setMessage("please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        categoryModelList = new ArrayList<>();
        categoryAdpter = new CategoryAdpter(getContext(), categoryModelList);
        catRecyclerView.setAdapter(categoryAdpter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdpter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        }else{
                            Toast.makeText(getActivity(), "sanyas "+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // New Products

        productRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newProductsModelList = new ArrayList<>();
        newProdutsAdapter = new NewProdutsAdapter(getContext(), newProductsModelList);
        productRecyclerView.setAdapter(newProdutsAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProductsModel newProductsModel = document.toObject(NewProductsModel.class);
                                newProductsModelList.add(newProductsModel);
                                newProdutsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "abc " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Popular recyclerView
        popularRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        popularProductModelsList = new ArrayList<>();
        popularProductAdpater = new PopularProductAdpater(getContext(), popularProductModelsList);
        popularRecyclerView.setAdapter(popularProductAdpater);

        db.collection("AllProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularProductModel popularProductModel = document.toObject(PopularProductModel.class);
                                popularProductModelsList.add(popularProductModel);
                                popularProductAdpater.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), "cfk " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}