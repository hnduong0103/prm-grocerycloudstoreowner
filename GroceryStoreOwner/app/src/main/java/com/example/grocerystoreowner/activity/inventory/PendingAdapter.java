package com.example.grocerystoreowner.activity.inventory;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.grocerystoreowner.activity.receipt.ViewReceiptDetailActivity;
import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.example.grocerystoreowner.model.product.StockData;
import com.example.grocerystoreowner.util.DataLocalManager;
import com.google.gson.Gson;

import java.util.List;

public class PendingAdapter extends BaseAdapter implements ListAdapter {
    private List<PendingItem> _pendinglist;

    public void setPendingList(List<PendingItem> _pendinglist){
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
            view = inflater.inflate(R.layout.pending_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.ProductName);
        EditText quantity = view.findViewById(R.id.Quantity);
        EditText buyprice = view.findViewById(R.id.Price);
        Button btDelete = view.findViewById(R.id.btDelete);
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        PendingItem item = pendingList.get(i);
        productName.setText(item.getProductName());
        quantity.setText(String.valueOf(item.getQuantity()));
        buyprice.setText(String.valueOf(item.getBuyPrice()));
        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Remove "+pendingList.get(i).getProductName(), Toast.LENGTH_SHORT).show();
                pendingList.remove(i);
                DataLocalManager.setPendingList(pendingList);
                Intent intent = new Intent(view.getContext(), ViewPendingList.class);
                view.getContext().startActivity(intent);
            }
        });
        buyprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int index = getIndexOf(item.getProductId());
                pendingList.get(index).setBuyPrice(Integer.parseInt(buyprice.getText().toString()));
                DataLocalManager.setPendingList(pendingList);
            }
        });
        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int index = getIndexOf(item.getProductId());
                pendingList.get(index).setQuantity(Integer.parseInt(quantity.getText().toString()));
                DataLocalManager.setPendingList(pendingList);
            }
        });
        return view;
    }

    private int getIndexOf(int id){
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        for (int i = 0; i<pendingList.size(); i++){
            if (pendingList.get(i).getProductId() == id){
                return i;
            }
        }
        return -1;
    }
}
