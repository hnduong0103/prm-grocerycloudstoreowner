package com.example.grocerystoreowner.activity.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.product.StockData;

import java.util.List;

public class InventoryAdapter extends BaseAdapter implements ListAdapter {

    private List<StockData> _productlist;

    public void setProductList(List<StockData> _productlist){
        this._productlist = _productlist;
    }

    @Override
    public int getCount() {
        return _productlist.size();
    }

    @Override
    public Object getItem(int i) {
        return _productlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _productlist.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup){
        View view = convertView;
        if (view == null){
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.inventory_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.ProductName);
        TextView quantity = view.findViewById(R.id.Quantity);
        TextView unit = view.findViewById(R.id.Unit);
        Button btClickDetail = view.findViewById(R.id.btImport);
        StockData _stockData = _productlist.get(i);
        productName.setText(_stockData.getName());
        quantity.setText(String.valueOf(_stockData.getCurrentQuantity()));
        unit.setText(_stockData.getUnitLabel());
        return view;
    }
}
