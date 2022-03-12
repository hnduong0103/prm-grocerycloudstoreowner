package com.example.grocerystoreowner.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.bill.BillDetail;
import com.google.gson.Gson;

import java.util.List;

public class ViewBillDetailActivity extends AppCompatActivity {
    private GridView gvBillDetail;
    BillDetailAdapter billDetailAdapter = new BillDetailAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill_detail);
        gvBillDetail = findViewById(R.id.gvBillDetail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = this.getIntent();
        BillData _bill = new Gson().fromJson((String) intent.getSerializableExtra("bill"),BillData.class) ;
        List<BillDetail> detailList = _bill.getBillDetails();
        billDetailAdapter.setBillDetailList(detailList);
        gvBillDetail.setAdapter(billDetailAdapter);
    }
}