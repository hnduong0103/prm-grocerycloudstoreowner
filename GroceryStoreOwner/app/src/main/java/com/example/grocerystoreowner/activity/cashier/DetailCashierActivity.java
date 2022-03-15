package com.example.grocerystoreowner.activity.cashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.store.Store;
import com.example.grocerystoreowner.model.cashier.Cashier;
import com.example.grocerystoreowner.service.CashierService;
import com.example.grocerystoreowner.service.StoreService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCashierActivity extends AppCompatActivity {

    private Cashier cashier;
    private List<Store> storeList;
    private EditText edtName, edtUsername, edtPassword;
    private AutoCompleteTextView spnStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cashier);

        Button btnConfirm = findViewById(R.id.btn_confirm_update_cashier);
        btnConfirm.setOnClickListener(view -> clickToUpdate());

        Button btnCancel = findViewById(R.id.btn_cancel_update_cashier);
        btnCancel.setOnClickListener(view -> clickToCancel());

        edtName = findViewById(R.id.edt_cashier_name);
        spnStore = findViewById(R.id.spn_cashier_store);
        edtUsername = findViewById(R.id.edt_cashier_username);
        edtPassword = findViewById(R.id.edt_cashier_password);

        Intent intent = this.getIntent();
        cashier = (Cashier) intent.getSerializableExtra("cashier");

        if (cashier != null) {
            spnStore.setInputType(InputType.TYPE_NULL);
            edtUsername.setInputType(InputType.TYPE_NULL);
        }

        fetchStoreList();

        if (cashier != null) {
            edtName.setText(cashier.getName());
            spnStore.setText(cashier.getStoreName());
            edtUsername.setText(cashier.getUsername());
        }
    }

    public void clickToUpdate() {
        String name = edtName.getText().toString().trim();
        int storeId = findStoreIdByName(spnStore.getText().toString().trim());
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (name.isEmpty()) {
            notify("Vui lòng nhập tên nhân viên.");
            return;
        }

        if (storeId == -1) {
            notify("Cửa hàng không hợp lệ.");
            return;
        }

        if (username.isEmpty()) {
            notify("Vui lòng nhập tên đăng nhập.");
            return;
        }

        if (password.isEmpty()) {
            notify("Vui lòng nhập mật khẩu.");
            return;
        }

        Cashier newCashier = new Cashier(
                name,
                storeId,
                username,
                password
        );

        Call<Void> api;

        if (cashier == null) {
            api = CashierService.getApi().createCashier(newCashier);
        } else {
            api = CashierService.getApi().updateCashier(cashier.getId(), newCashier);
        }

        api.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toast.makeText(DetailCashierActivity.this, "Cập nhật nhân viên thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(DetailCashierActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }

    public void clickToCancel() {
        finish();
    }

    public void fetchStoreList() {
        StoreService
                .getApi()
                // TODO: add brand ID
                .getStoreList(1)
                .enqueue(new Callback<List<Store>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Store>> call, @NonNull Response<List<Store>> response) {
                        if (response.body() != null) {
                            storeList = response.body();
                            List<String> storeNameList = new ArrayList<>();
                            for (Store store : storeList) {
                                storeNameList.add(store.getName());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DetailCashierActivity.this, android.R.layout.select_dialog_item, storeNameList);
                            spnStore.setThreshold(1); //will start working from first character
                            spnStore.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Store>> call, @NonNull Throwable t) {
                        Toast.makeText(DetailCashierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public int findStoreIdByName(String storeName) {
        for (Store store : storeList) {
            if (storeName.equals(store.getName())) {
                return store.getId();
            }
        }
        return -1;
    }

    private void notify(String message) {
        Toast.makeText(DetailCashierActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}