package com.example.grocerystoreowner.activity.receipt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.bill.BillDetail;
import com.example.grocerystoreowner.model.receipt.ReceiptDetail;

import java.util.List;

public class ReceiptDetailAdapter extends BaseAdapter implements ListAdapter {
    private List<ReceiptDetail> _receiptDetailList;

    public void setReceiptDetailList(List<ReceiptDetail> _receiptDetailList){
        this._receiptDetailList = _receiptDetailList;
    }

    @Override
    public int getCount() {
        return _receiptDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return _receiptDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _receiptDetailList.get(i).getProductId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.receipt_detail_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.ProductName);
        TextView quantity = view.findViewById(R.id.Quantity);
        TextView price = view.findViewById(R.id.Price);
        ReceiptDetail _receiptDetail = _receiptDetailList.get(i);
        productName.setText(_receiptDetail.getProductName());
        quantity.setText(String.valueOf(_receiptDetail.getQuantity()));
        price.setText(String.valueOf(_receiptDetail.getBuyPrice()));
        return view;
    }
}
