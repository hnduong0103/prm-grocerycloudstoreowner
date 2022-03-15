package com.example.grocerystoreowner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.cashier.Cashier;
import com.example.grocerystoreowner.util.Constants;

import java.util.List;

public class CashierAdapter  extends BaseAdapter implements ListAdapter {

    private List<Cashier> _cashierList;

    public void setCashierList(List<Cashier> list) {
        _cashierList = list;
    }

    @Override
    public int getCount() {
        return _cashierList.size();
    }

    @Override
    public Object getItem(int i) {
        return _cashierList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _cashierList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.cashier_list_item, viewGroup, false);
        }

        TextView name, store, username, status;

        name = view.findViewById(R.id.cashier_item_name);
        store = view.findViewById(R.id.cashier_item_store);
        username = view.findViewById(R.id.cashier_item_username);
        status = view.findViewById(R.id.cashier_item_status);

        Cashier cashier = _cashierList.get(i);

        name.setText(cashier.getName());
        store.setText(cashier.getStoreName());
        username.setText(cashier.getUsername());
        status.setText(Constants.CASHIER_STATUS[cashier.getStatus()]);

        return view;
    }
}

