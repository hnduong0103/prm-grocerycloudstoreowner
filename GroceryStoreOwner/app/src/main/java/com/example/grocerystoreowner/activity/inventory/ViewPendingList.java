package com.example.grocerystoreowner.activity.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.BillDetailAdapter;
import com.example.grocerystoreowner.activity.bill.ViewBillActivity;
import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.example.grocerystoreowner.util.DataLocalManager;

import java.util.List;

public class ViewPendingList extends AppCompatActivity {
    private GridView gvPendingList;
    private TextView TotalCost;
    PendingAdapter pendingAdapter = new PendingAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_list);
        gvPendingList = findViewById(R.id.gvPendingList);
        TotalCost = findViewById(R.id.TotalCost);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        pendingAdapter.setPendingList(pendingList);
        gvPendingList.setAdapter(pendingAdapter);
        int tempTotal = 0;
        for (int i = 0; i<pendingList.size(); i++){
            tempTotal += pendingList.get(i).getBuyPrice()*pendingList.get(i).getQuantity();
        }
        TotalCost.setText(String.valueOf(tempTotal));
    }

    public void clickToGoToInventory(View view) {
        finish();
    }

    public void clickToGoToCreateReceipt(View view) {
        Intent intent = new Intent(this, CreateReceiptActivity.class);
        startActivity(intent);
    }
}