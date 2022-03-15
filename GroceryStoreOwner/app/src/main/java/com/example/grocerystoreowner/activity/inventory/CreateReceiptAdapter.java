package com.example.grocerystoreowner.activity.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.example.grocerystoreowner.model.product.StockData;
import com.example.grocerystoreowner.util.DataLocalManager;

import java.util.List;

public class CreateReceiptAdapter extends BaseAdapter implements ListAdapter {
    private List<PendingItem> _pendinglist;

    public void setReceiptList(List<PendingItem> _pendinglist){
        this._pendinglist = _pendinglist;
    }

    @Override
    public int getCount() {
        return _pendinglist.size();
    }

    @Override
    public Object getItem(int i) {
        return _pendinglist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _pendinglist.get(i).getProductId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.create_receipt_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.ProductName);
        TextView quantity = view.findViewById(R.id.Quantity);
        TextView buyprice = view.findViewById(R.id.Price);
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        PendingItem item = pendingList.get(i);
        productName.setText(item.getProductName());
        quantity.setText(String.valueOf(item.getQuantity()));
        buyprice.setText(String.valueOf(item.getBuyPrice()));
        return view;
    }
}
