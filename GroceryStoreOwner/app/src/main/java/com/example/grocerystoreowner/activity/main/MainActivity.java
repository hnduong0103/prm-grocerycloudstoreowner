package com.example.grocerystoreowner.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.ViewBillActivity;
import com.example.grocerystoreowner.activity.login.LoginActivity;
import com.example.grocerystoreowner.activity.receipt.ViewReceiptActivity;
import com.example.grocerystoreowner.activity.product.ViewProductActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToViewBill(View view) {
        Intent intent = new Intent(this, ViewBillActivity.class);
        startActivity(intent);
    }

    public void clickToGoToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickToGoToReceipt(View view) {
        Intent intent = new Intent(this, ViewReceiptActivity.class);
        startActivity(intent);
    }

    public void clickToViewProducts(View view) {
        Intent intent = new Intent(this, ViewProductActivity.class);
        startActivity(intent);
    }
}