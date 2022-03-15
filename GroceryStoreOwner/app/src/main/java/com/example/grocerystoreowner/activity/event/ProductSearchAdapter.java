package com.example.grocerystoreowner.activity.event;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventStatusPutRequest;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponse;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponseList;
import com.example.grocerystoreowner.service.EventService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductSearchAdapter extends BaseAdapter implements ListAdapter {
    private ProductSearchActivity _ac;
    public ProductSearchAdapter(ProductSearchActivity ac)
    {
        _ac = ac;
    }

    public void setProductList(List<ProductSearchInEventResponse> _productList) {
        this._productList = _productList;
    }
    private List<ProductSearchInEventResponse> _productList;

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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.product_for_event_search_grid_item, viewGroup, false);
        }
        TextView txtName = view.findViewById(R.id.txtName);
        TextView txtSku = view.findViewById(R.id.txtSku);
        TextView txtPrice = view.findViewById(R.id.txtPrice);

        ProductSearchInEventResponse product = _productList.get(i);
        txtName.setText(product.getName());
        txtSku.setText(product.getSku());
        txtPrice.setText(new Integer(product.getSellPrice()).toString());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
                SharedPreferenceUtil.putKey(sharedPreferences, "ProductToAddToEvent", new Gson().toJson(product));
                SharedPreferenceUtil.putKey(sharedPreferences, "HasProductToAdd", "1");
                _ac.finish();
            }
        });
        return view;
    }
}
