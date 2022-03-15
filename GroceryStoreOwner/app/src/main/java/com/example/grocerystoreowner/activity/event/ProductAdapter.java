package com.example.grocerystoreowner.activity.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.event.EventDetailResponse;

import java.util.List;

public class ProductAdapter extends BaseAdapter implements ListAdapter {
    private void editEventDetail(int index, int newPrice) {
        EventDetailResponse eventDetail = this._eventDetailList.get(index);
        eventDetail.setNewPrice(newPrice);
        notifyDataSetChanged();
    }
    private void deleteEventDetail(int index) {
        this._eventDetailList.remove(index);
        notifyDataSetChanged();

    }

    public void setEventDetailList(List<EventDetailResponse> _eventDetailList) {
        this._eventDetailList = _eventDetailList;
        notifyDataSetChanged();
    }
    public List<EventDetailResponse> getEventDetailList() {
        return this._eventDetailList;
    }
//    private EventListActivity _activity;
    private List<EventDetailResponse> _eventDetailList;

    @Override
    public int getCount() {
        return _eventDetailList.size();
    }

    @Override
    public Object getItem(int i) {
        return _eventDetailList.get(i);
    }

    @Override
    public long getItemId(int i) {
      return  _eventDetailList.get(i).getProductId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.product_in_event_grid_item, viewGroup, false);
        }
        TextView productName = view.findViewById(R.id.txtProductName);
        TextView txtOriginalPrice = view.findViewById(R.id.txtOriginalPrice);
        EditText edtNewPrice = view.findViewById(R.id.edtNewPrice);
        edtNewPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    editEventDetail(i,Integer.parseInt(edtNewPrice.getText().toString()));
                }
            }
        });


        EventDetailResponse eventDetail = _eventDetailList.get(i);

        productName.setText(eventDetail.getProductName());
        txtOriginalPrice.setText(new Integer(eventDetail.getOriginalPrice()).toString());
        edtNewPrice.setText(new Integer(eventDetail.getNewPrice()).toString());
        //set logic cho btn
        Button btnDelete = view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteEventDetail(i);
            }
        });

        return view;
    }
}
