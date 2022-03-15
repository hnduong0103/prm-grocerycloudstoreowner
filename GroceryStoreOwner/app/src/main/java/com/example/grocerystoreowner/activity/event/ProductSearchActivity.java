package com.example.grocerystoreowner.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.brand.SwitchBrandActivity;
import com.example.grocerystoreowner.model.brand.BrandResponse;
import com.example.grocerystoreowner.model.event.EventResponseList;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponse;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponseList;
import com.example.grocerystoreowner.service.ProductEventService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearchActivity extends AppCompatActivity {
    private EditText edtSearchTerm;
    private Button btnSearch;
    private GridView productGridView;
    ProductSearchAdapter productAdapter = new ProductSearchAdapter(ProductSearchActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_search);
        edtSearchTerm = findViewById(R.id.edtSearchTerm);
        btnSearch = findViewById(R.id.btnSearch);
        productGridView = findViewById(R.id.productGridView);
    }
    private void fetchData(int brandId, String searchTerm, int pageIndex, int pageSize) {
        ProductEventService.getApi().getProductlist(brandId,searchTerm,pageIndex,pageSize)
                .enqueue(new Callback<ProductSearchInEventResponseList>() {
            @Override
            public void onResponse(Call<ProductSearchInEventResponseList> call, Response<ProductSearchInEventResponseList> response) {
                List<ProductSearchInEventResponse> listProduct = (List<ProductSearchInEventResponse>) response.body().getData();
                productAdapter.setProductList(listProduct);
                productGridView.setAdapter(productAdapter);
            }
            @Override
            public void onFailure(Call<ProductSearchInEventResponseList> call, Throwable t) {
                Toast.makeText(ProductSearchActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
            }
                });

    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        fetchData(brandId,"",1,9999);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = edtSearchTerm.getText().toString();
                fetchData(brandId,searchTerm,1,9999);
            }
        });
    }
}