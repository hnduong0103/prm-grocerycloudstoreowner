package com.example.grocerystoreowner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.util.Constants;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ProductAdapter extends BaseAdapter implements ListAdapter {

    private List<Product> _productList;

    public void setProductList(List<Product> list) {
        _productList = list;
    }

    @Override
    public int getCount() {
        return _productList.size();
    }

    @Override
    public Object getItem(int i) {
        return _productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _productList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.product_list_item, viewGroup, false);
        }

        TextView name, sku, category, sellPrice, unitLabel, status;

        name = view.findViewById(R.id.product_item_name);
        sku = view.findViewById(R.id.product_item_sku);
        category = view.findViewById(R.id.product_item_category);
        sellPrice = view.findViewById(R.id.product_item_sell_price);
        unitLabel = view.findViewById(R.id.product_item_unit_label);
        status = view.findViewById(R.id.product_item_status);

        Product product = _productList.get(i);

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###đ");
        decimalFormat.setGroupingSize(3);

        name.setText(product.getName());
        sku.setText(product.getSku());
        category.setText(product.getCategoryName());
        sellPrice.setText(decimalFormat.format(product.getSellPrice()));
        unitLabel.setText(String.valueOf(product.getUnitLabel()));
        status.setText(Constants.PRODUCT_STATUS[product.getStatus()]);

        return view;
    }
}
