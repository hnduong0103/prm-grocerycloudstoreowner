package com.example.grocerystoreowner.activity.receipt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.bill.BillDetailAdapter;
import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.bill.BillDetail;
import com.example.grocerystoreowner.model.receipt.ReceiptData;
import com.example.grocerystoreowner.model.receipt.ReceiptDetail;
import com.google.gson.Gson;

import java.util.List;

public class ViewReceiptDetailActivity extends AppCompatActivity {
    private GridView gvReceiptDetail;
    ReceiptDetailAdapter receiptDetailAdapter = new ReceiptDetailAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt_detail);
        gvReceiptDetail = findViewById(R.id.gvReceiptDetail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = this.getIntent();
        ReceiptData _receipt = new Gson().fromJson((String) intent.getSerializableExtra("receipt"), ReceiptData.class) ;
        List<ReceiptDetail> detailList = _receipt.getReceiptDetails();
        receiptDetailAdapter.setReceiptDetailList(detailList);
        gvReceiptDetail.setAdapter(receiptDetailAdapter);
    }
}