package com.example.grocerystoreowner.activity.store;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.ViewBillActivity;
import com.example.grocerystoreowner.activity.brand.SwitchBrandActivity;
import com.example.grocerystoreowner.activity.inventory.ViewInventoryActivity;
import com.example.grocerystoreowner.activity.main.MainActivity;
import com.example.grocerystoreowner.activity.receipt.ViewReceiptActivity;
import com.example.grocerystoreowner.model.brand.BrandResponse;
import com.example.grocerystoreowner.model.store.Store;
import com.example.grocerystoreowner.service.StoreService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchStoreActivity extends AppCompatActivity {
    private ListView listStoreListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_store);
        listStoreListView = findViewById(R.id.storeLíst);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        StoreService.getApi().getStoreList(brandId)
                .enqueue(new Callback<List<Store>>() {
                    @Override
                    public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                        Toast.makeText(SwitchStoreActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        List<Store> listStore = (List<Store>) response.body();
                        ArrayAdapter<Store> adapter =
                                new ArrayAdapter<>(SwitchStoreActivity.this, android.R.layout.simple_list_item_1, listStore);
                        listStoreListView.setAdapter(adapter);
                        listStoreListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Store store = (Store) listStoreListView.getItemAtPosition(i);
                                Toast.makeText(SwitchStoreActivity.this, "Store selected: "+store.getName(), Toast.LENGTH_SHORT).show();
                                SharedPreferenceUtil.putKey(sharedPreferences,
                                        Constants.STORE_ID_SHARED_PREFERENCE,
                                        new Integer(store.getId()).toString());
                                Intent thisintent = getIntent();
                                int action = thisintent.getIntExtra("action",0);
                                switch (action){
                                    case 1: {
                                        Intent intent = new Intent(SwitchStoreActivity.this, ViewBillActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case 2: {
                                        Intent intent = new Intent(SwitchStoreActivity.this, ViewReceiptActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case 3: {
                                        Intent intent = new Intent(SwitchStoreActivity.this, ViewInventoryActivity.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    default:{
                                        finish();
                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Store>> call, Throwable t) {
                        Toast.makeText(SwitchStoreActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}