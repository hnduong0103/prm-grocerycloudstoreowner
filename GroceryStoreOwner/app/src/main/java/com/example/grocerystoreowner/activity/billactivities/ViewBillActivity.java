package com.example.grocerystoreowner.activity.billactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.login.LoginActivity;
import com.example.grocerystoreowner.api.BillAPI;
import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.service.BillService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBillActivity extends AppCompatActivity {
    private ListView listbill;
    private Button btnCallAPI;
    private TextView tv_startDate, tv_endDate;
    private EditText edt_storeID;
    DatePickerDialog.OnDateSetListener setListenerStartDate, setListenerEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        listbill = findViewById(R.id.lv_billlist);
        btnCallAPI = findViewById(R.id.btn_callAPI);

        edt_storeID = findViewById(R.id.edt_storeID);
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_endDate = findViewById(R.id.tv_endDate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tv_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ViewBillActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListenerStartDate, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListenerStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "-" + day + "-" + year;
                tv_startDate.setText(date);
            }
        };

        tv_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ViewBillActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        setListenerEndDate, year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "-" + day + "-" + year;
                tv_endDate.setText(date);
            }
        };

        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickToCallAPI();
            }
        });
    }

    public void clickToCallAPI() {
        int storeID = Integer.parseInt(edt_storeID.getText().toString());
        String startDate = tv_startDate.getText().toString();
        String endDate = tv_endDate.getText().toString();
        BillService.getApi().getBillList(storeID, startDate, endDate)
                .enqueue(new Callback<BillResponse>() {
                    @Override
                    public void onResponse(Call<BillResponse> call, Response<BillResponse> response) {
                        Toast.makeText(ViewBillActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        List<String> renderItem = new ArrayList<>();
                        ArrayList<BillData> billData = (ArrayList<BillData>) response.body().getData();
                        for (int i = 0; i < billData.size(); i++) {
/*                    renderItem.add(String.valueOf(billData.get(i).getId()));
                    renderItem.add(billData.get(i).getCashierName());
                    renderItem.add(billData.get(i).getDateCreated());
                    renderItem.add(String.valueOf(billData.get(i).getTotalPrice()));
                    renderItem.add(" ");*/
                            String billTempItem = "";
                            billTempItem += (billData.get(i).getId()) + "\n";
                            billTempItem += billData.get(i).getCashierName() + "\n";
                            billTempItem += billData.get(i).getDateCreated() + "\n";
                            billTempItem += billData.get(i).getTotalPrice() + "\n";
                            billTempItem += "\n";
                            renderItem.add(billTempItem);
                        }
                        ArrayAdapter<String> adapter =
                                new ArrayAdapter<>(ViewBillActivity.this, android.R.layout.simple_list_item_1, renderItem);
                        listbill.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<BillResponse> call, Throwable t) {
                        Toast.makeText(ViewBillActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}