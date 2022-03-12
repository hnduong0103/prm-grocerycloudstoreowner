package com.example.grocerystoreowner.activity.receipt;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.receipt.ReceiptData;
import com.google.gson.Gson;

import java.util.List;

public class ReceiptAdapter extends BaseAdapter implements ListAdapter {
    private List<ReceiptData> _receiptList;

    public void setReceiptList(List<ReceiptData> _receiptList){
        this._receiptList = _receiptList;
    }

    @Override
    public int getCount() {
        return _receiptList.size();
    }

    @Override
    public Object getItem(int i) {
        return _receiptList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _receiptList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.receipt_grid_item, viewGroup, false);
        }
        TextView receiptID = view.findViewById(R.id.receiptID);
        TextView createdDate = view.findViewById(R.id.CreatedDate);
        TextView totalPrice = view.findViewById(R.id.Total);
        Button btClickDetail = view.findViewById(R.id.btDetail);
        ReceiptData _receipt = _receiptList.get(i);
        receiptID.setText(String.valueOf(_receipt.getId()));
        createdDate.setText(_receipt.getDateCreated().substring(0,10));
        totalPrice.setText(String.valueOf(_receipt.getTotalCost()));
        btClickDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewReceiptDetailActivity.class);
                intent.putExtra("receipt", (new Gson().toJson(_receipt)));
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
