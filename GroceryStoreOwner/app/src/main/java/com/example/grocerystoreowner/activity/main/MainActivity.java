package com.example.grocerystoreowner.activity.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.ViewBillActivity;
import com.example.grocerystoreowner.activity.event.EventListActivity;
import com.example.grocerystoreowner.activity.inventory.ViewInventoryActivity;
import com.example.grocerystoreowner.activity.cashier.ViewCashierActivity;
import com.example.grocerystoreowner.activity.login.LoginActivity;
import com.example.grocerystoreowner.activity.receipt.ViewReceiptActivity;
import com.example.grocerystoreowner.activity.product.ViewProductActivity;
import com.example.grocerystoreowner.activity.store.SwitchStoreActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickToViewBill(View view) {
        Intent intent = new Intent(this, SwitchStoreActivity.class);
        intent.putExtra("action", 1);
        startActivity(intent);
    }

    public void clickToGoToLogin(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickToGoToReceipt(View view) {
        Intent intent = new Intent(this, SwitchStoreActivity.class);
        intent.putExtra("action", 2);
        startActivity(intent);
    }

    public void clickToGoToInventory(View view) {
        Intent intent = new Intent(this, SwitchStoreActivity.class);
        intent.putExtra("action", 3);
        startActivity(intent);
        }
    public void clickToViewProducts(View view) {
        Intent intent = new Intent(this, ViewProductActivity.class);
        startActivity(intent);
    }

    public void clickToViewCashiers(View view) {
        Intent intent = new Intent(this, ViewCashierActivity.class);
        startActivity(intent);
    }

    public void clickToViewEvent(View view) {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }
}