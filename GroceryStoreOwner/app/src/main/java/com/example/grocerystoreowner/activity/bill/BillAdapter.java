package com.example.grocerystoreowner.activity.bill;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.bill.BillData;
import com.google.gson.Gson;

import java.util.List;

public class BillAdapter extends BaseAdapter implements ListAdapter {

    private List<BillData> _billList;

    public void setBillList(List<BillData> _billList){
        this._billList = _billList;
    }

    @Override
    public int getCount() {
        return _billList.size();
    }

    @Override
    public Object getItem(int i) {
        return _billList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _billList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup){
        View view = convertView;
            if (view == null){
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                view = inflater.inflate(R.layout.bill_grid_item, viewGroup, false);
            }
            TextView billID = view.findViewById(R.id.billID);
            TextView cashierName = view.findViewById(R.id.CashierName);
            TextView createdDate = view.findViewById(R.id.CreatedDate);
            TextView totalPrice = view.findViewById(R.id.Total);
            Button btClickDetail = view.findViewById(R.id.btDetail);
            BillData _bill = _billList.get(i);
            billID.setText(String.valueOf(_bill.getId()));
            cashierName.setText(_bill.getCashierName());
            createdDate.setText(_bill.getDateCreated().substring(0,10));
            totalPrice.setText(String.valueOf(_bill.getTotalPrice()));
            btClickDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ViewBillDetailActivity.class);
                intent.putExtra("bill", (new Gson().toJson(_bill)));
                view.getContext().startActivity(intent);
            }
        });
            return view;
    }
}
