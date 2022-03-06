package com.example.grocerystoreowner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.grocerystoreowner.screenactivities.billactivities.ViewBillActivity;

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
}