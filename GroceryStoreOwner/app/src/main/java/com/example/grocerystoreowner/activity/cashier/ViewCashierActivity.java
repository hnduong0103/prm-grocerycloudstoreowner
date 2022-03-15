package com.example.grocerystoreowner.activity.cashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.adapter.CashierAdapter;
import com.example.grocerystoreowner.model.cashier.Cashier;
import com.example.grocerystoreowner.model.cashier.CashierResponse;
import com.example.grocerystoreowner.service.CashierService;
import com.example.grocerystoreowner.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCashierActivity extends AppCompatActivity {

    private ListView lvCashiers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cashier);

        lvCashiers = findViewById(R.id.lv_cashiers);
        fetchCashier(false);

        Button btnCreate = findViewById(R.id.btn_create_cashier);
        btnCreate.setOnClickListener(view -> clickToCreateCashier());

        CheckBox cbxIncludeDisabledCashier = findViewById(R.id.cbx_include_disabled_cashier);
        cbxIncludeDisabledCashier.setOnCheckedChangeListener((compoundButton, isChecked) -> fetchCashier(isChecked));
    }

    public void fetchCashier(boolean isIncludeDisabledCashier) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        CashierService
                .getApi()
                // TODO: add brandID and storeID
                .getCashierList(brandId, null, null, isIncludeDisabledCashier)
                .enqueue(new Callback<CashierResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CashierResponse> call, @NonNull Response<CashierResponse> response) {
                        CashierResponse cashierResponse = response.body();
                        if (cashierResponse != null) {
                            CashierAdapter adapter = new CashierAdapter();
                            adapter.setCashierList(cashierResponse.getData());
                            lvCashiers.setAdapter(adapter);

                            lvCashiers.setOnItemClickListener((adapterView, view, i, l) -> {
                                Cashier cashier = (Cashier) lvCashiers.getItemAtPosition(i);
                                Intent intent = new Intent(ViewCashierActivity.this, DetailCashierActivity.class);
                                intent.putExtra("cashier", cashier);
                                startActivity(intent);
                            });

                            lvCashiers.setOnItemLongClickListener((adapterView, view, i, l) -> {
                                Cashier cashier = (Cashier) lvCashiers.getItemAtPosition(i);
                                Intent intent = new Intent(ViewCashierActivity.this, DeleteCashierActivity.class);
                                intent.putExtra("cashier", cashier);
                                startActivity(intent);
                                return true;
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CashierResponse> call, @NonNull Throwable t) {
                        Toast.makeText(ViewCashierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickToCreateCashier() {
        Intent intent = new Intent(ViewCashierActivity.this, DetailCashierActivity.class);
        startActivity(intent);
    }
}