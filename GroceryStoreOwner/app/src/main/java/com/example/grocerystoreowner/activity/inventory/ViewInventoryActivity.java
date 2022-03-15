package com.example.grocerystoreowner.activity.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.ViewBillActivity;
import com.example.grocerystoreowner.model.product.StockData;
import com.example.grocerystoreowner.model.product.StockResponse;
import com.example.grocerystoreowner.service.StockService;
import com.example.grocerystoreowner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewInventoryActivity extends AppCompatActivity {
    private GridView gvInventory;
    InventoryAdapter inventoryAdapter = new InventoryAdapter();
    InventoryAdapter searchItemAdapter = new InventoryAdapter();
    List<StockData> searchedItem;
    List<StockData> stockDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inventory);
        gvInventory = findViewById(R.id.gvInventory);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        int storeid = 1;
        String searchTerm = "";
        StockService.getApi().getStock(brandId, storeid, searchTerm, 100)
                .enqueue(new Callback<StockResponse>() {
                    @Override
                    public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                        Toast.makeText(ViewInventoryActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        stockDataList = response.body().getData();
                        inventoryAdapter.setProductList(stockDataList);
                        gvInventory.setAdapter(inventoryAdapter);
                    }

                    @Override
                    public void onFailure(Call<StockResponse> call, Throwable t) {
                        Toast.makeText(ViewInventoryActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickReset(View view) {
        gvInventory.setAdapter(inventoryAdapter);
    }

    public void clickSearch(View view) {
        EditText edtsearchTerm = findViewById(R.id.searchTerm);
        int brandid = 1;
        int storeid = 1;
        String searchTerm = edtsearchTerm.getText().toString();
        StockService.getApi().getStock(brandid, storeid, searchTerm, 100)
                .enqueue(new Callback<StockResponse>() {
                    @Override
                    public void onResponse(Call<StockResponse> call, Response<StockResponse> response) {
                        Toast.makeText(ViewInventoryActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        searchedItem = response.body().getData();
                        searchItemAdapter.setProductList(searchedItem);
                        gvInventory.setAdapter(searchItemAdapter);
                    }

                    @Override
                    public void onFailure(Call<StockResponse> call, Throwable t) {
                        Toast.makeText(ViewInventoryActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void clickToPendingList(View view) {
        Intent intent = new Intent(this, ViewPendingList.class);
        startActivity(intent);
    }
}