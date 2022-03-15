package com.example.grocerystoreowner.activity.inventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.example.grocerystoreowner.model.product.StockData;
import com.example.grocerystoreowner.util.DataLocalManager;

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
        Button btClickImport = view.findViewById(R.id.btImport);
        StockData _stockData = _productlist.get(i);
        productName.setText(_stockData.getName());
        quantity.setText(String.valueOf(_stockData.getCurrentQuantity()));
        unit.setText(_stockData.getUnitLabel());
        btClickImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<PendingItem> pendingList = DataLocalManager.getPendingList();
                int foundIndex = containsItem(_stockData.getId());
                if (foundIndex != -1){
                    int currentQuantity = pendingList.get(foundIndex).getQuantity();
                    currentQuantity++;
                    pendingList.get(foundIndex).setQuantity(currentQuantity);
                    DataLocalManager.setPendingList(pendingList);
                    Toast.makeText(view.getContext(),"Add quantity to "+pendingList.get(foundIndex).getProductName(), Toast.LENGTH_SHORT).show();
                } else {
                    PendingItem item = new PendingItem(_stockData.getId(),_stockData.getName(), 1, 0);
                    pendingList.add(item);
                    DataLocalManager.setPendingList(pendingList);
                    Toast.makeText(view.getContext(),"Add new Item", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private int containsItem(int id){
        List<PendingItem> pendingList = DataLocalManager.getPendingList();
        for (int i = 0; i<pendingList.size(); i++){
            if (pendingList.get(i).getProductId() == id){
                return i;
            }
        }
        return -1;
    }
}
