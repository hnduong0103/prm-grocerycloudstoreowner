package com.example.grocerystoreowner.activity.brand;

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
import com.example.grocerystoreowner.activity.event.EventListActivity;
import com.example.grocerystoreowner.model.brand.BrandResponse;
import com.example.grocerystoreowner.service.BrandService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SwitchBrandActivity extends AppCompatActivity {
    private ListView listBrandListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_brand);
        listBrandListView = findViewById(R.id.brandList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer userId = Integer.parseInt(sharedPreferences.getString(Constants.USER_ID_SHARED_PREFERENCE,null));
        BrandService.getApi().getBrands(userId)
                .enqueue(new Callback<List<BrandResponse>>() {
                    @Override
                    public void onResponse(Call<List<BrandResponse>> call, Response<List<BrandResponse>> response) {
                        Toast.makeText(SwitchBrandActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        List<BrandResponse> listBrand = (List<BrandResponse>) response.body();
//                        List<String> renderItem = new ArrayList<>();
//                        for (int i = 0; i < listBrand.size(); i++) {
//                            renderItem.add(listBrand.get(i).getName());
//                        }
                        ArrayAdapter<BrandResponse> adapter =
                                new ArrayAdapter<>(SwitchBrandActivity.this, android.R.layout.simple_list_item_1, listBrand);
                        listBrandListView.setAdapter(adapter);
                        listBrandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                BrandResponse brand = (BrandResponse) listBrandListView.getItemAtPosition(i);
                                Toast.makeText(SwitchBrandActivity.this, "Brand selected: "+brand.getId(), Toast.LENGTH_SHORT).show();

                                SharedPreferenceUtil.putKey(sharedPreferences,
                                        Constants.BRAND_ID_SHARED_PREFERENCE,
                                        new Integer(brand.getId()).toString());
                                SharedPreferenceUtil.putKey(sharedPreferences, Constants.BRAND_NAME_SHARED_PREFERENCE, brand.getName());

                                Intent intent = new Intent(SwitchBrandActivity.this, EventListActivity.class);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<List<BrandResponse>> call, Throwable t) {
                        Toast.makeText(SwitchBrandActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}