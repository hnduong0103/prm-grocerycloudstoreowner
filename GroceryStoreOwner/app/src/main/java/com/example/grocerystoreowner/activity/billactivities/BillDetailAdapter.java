package com.example.grocerystoreowner.activity.billactivities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.bill.BillDetail;

import java.util.List;

public class BillDetailAdapter extends BaseAdapter implements ListAdapter {
    private List<BillDetail> _billDetailList;

    public void setBillDetailList(List<BillDetail> _billDetailList){
        this._billDetailList = _billDetailList;
    }

    @Override
    public int getCount() {
        return _billDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return _billDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _billDetailList.get(i).getProductId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.bill_detail_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.ProductName);
        TextView quantity = view.findViewById(R.id.Quantity);
        TextView price = view.findViewById(R.id.Price);
        BillDetail _billDetail = _billDetailList.get(i);
        productName.setText(_billDetail.getProductName());
        quantity.setText(String.valueOf(_billDetail.getQuantity()));
        price.setText(String.valueOf(_billDetail.getSellPrice()));
        return view;
    }
}
