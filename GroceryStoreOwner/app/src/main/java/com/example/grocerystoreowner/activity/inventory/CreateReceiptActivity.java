package com.example.grocerystoreowner.activity.inventory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.receipt.ViewReceiptActivity;
import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.example.grocerystoreowner.model.receipt.ReceiptCreate;
import com.example.grocerystoreowner.model.receipt.ReceiptCreateItem;
import com.example.grocerystoreowner.service.ReceiptService;
import com.example.grocerystoreowner.util.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReceiptActivity extends AppCompatActivity {
    private GridView gvReceiptList;
    private TextView TotalCost;
    private Button clickToCreateReceipt;
    CreateReceiptAdapter createReceiptAdapter = new CreateReceiptAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt);
        gvReceiptList = findViewById(R.id.gvReceiptList);
        TotalCost = findViewById(R.id.TotalCost);
        clickToCreateReceipt = findViewById(R.id.clickToCreateReceipt);
        clickToCreateReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickToCallAPI();
            }
        });
    }

    public void clickToCallAPI() {
        int storeId = 1;
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        List<ReceiptCreateItem> createItems = new ArrayList<>();
        int totalCost = 0;
        for (int i = 0; i<pendingList.size(); i++){
            totalCost += pendingList.get(i).getBuyPrice()*pendingList.get(i).getQuantity();
            int productId = pendingList.get(i).getProductId();
            int buyPrice = pendingList.get(i).getBuyPrice();
            int quantity = pendingList.get(i).getQuantity();
            ReceiptCreateItem item = new ReceiptCreateItem(productId, buyPrice, quantity);
            createItems.add(item);
        }
        ReceiptCreate receiptCreate = new ReceiptCreate();
        receiptCreate.setStoreId(storeId);
        receiptCreate.setTotalCost(totalCost);
        receiptCreate.setDetails(createItems);
        ReceiptService.getApi().createReceipt(receiptCreate)
                .enqueue(new Callback<ReceiptCreate>() {
                    @Override
                    public void onResponse(Call<ReceiptCreate> call, Response<ReceiptCreate> response) {
                        if (response.code() == 200){
                            Toast.makeText(CreateReceiptActivity.this, "Create Receipt Successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ReceiptCreate> call, Throwable t) {
                        Toast.makeText(CreateReceiptActivity.this, "Create Receipt Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        createReceiptAdapter.setReceiptList(pendingList);
        gvReceiptList.setAdapter(createReceiptAdapter);
        int tempTotal = 0;
        for (int i = 0; i<pendingList.size(); i++){
            tempTotal += pendingList.get(i).getBuyPrice()*pendingList.get(i).getQuantity();
        }
        TotalCost.setText(String.valueOf(tempTotal));
    }

    public void clickToGoToInventory(View view) {
        finish();
    }
}