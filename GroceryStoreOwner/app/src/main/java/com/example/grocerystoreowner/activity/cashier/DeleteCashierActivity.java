package com.example.grocerystoreowner.activity.cashier;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.cashier.Cashier;
import com.example.grocerystoreowner.service.CashierService;
import com.example.grocerystoreowner.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteCashierActivity extends AppCompatActivity {

    private Cashier cashier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cashier);

        TextView txtName = findViewById(R.id.txt_cashier_name);
        TextView txtStore = findViewById(R.id.txt_cashier_store);
        TextView txtUsername = findViewById(R.id.txt_cashier_username);
        TextView txtStatus = findViewById(R.id.txt_cashier_status);

        Intent intent = this.getIntent();
        cashier = (Cashier) intent.getSerializableExtra("cashier");

        txtName.setText(cashier.getName());
        txtStore.setText(cashier.getStoreName());
        txtUsername.setText(cashier.getUsername());
        txtStatus.setText(Constants.CASHIER_STATUS[cashier.getStatus()]);

        Button btnConfirm = findViewById(R.id.btn_confirm_delete_cashier);
        btnConfirm.setOnClickListener(view -> clickToDelete());

        Button btnCancel = findViewById(R.id.btn_cancel_delete_cashier);
        btnCancel.setOnClickListener(view -> clickToCancel());
    }

    public void clickToDelete() {
        CashierService
                .getApi()
                .deleteCashier(cashier.getId())
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DeleteCashierActivity.this, "Vô hiệu hóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteCashierActivity.this, "Vô hiệu hóa tài khoản thất bại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(DeleteCashierActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        finish();
    }

    public void clickToCancel() {
        finish();
    }
}