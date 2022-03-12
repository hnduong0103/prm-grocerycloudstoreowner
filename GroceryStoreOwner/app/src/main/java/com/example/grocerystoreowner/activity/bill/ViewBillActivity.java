package com.example.grocerystoreowner.activity.bill;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.service.BillService;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBillActivity extends AppCompatActivity {
    private GridView gvBill;
    BillAdapter billAdapter = new BillAdapter();
    private Button btnCallAPI;
    private TextView tv_startDate, tv_endDate;
    private EditText edt_storeID;
    DatePickerDialog.OnDateSetListener setListenerStartDate, setListenerEndDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);

        gvBill = findViewById(R.id.gvBill);
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
                        List<BillData> billList = response.body().getData();
                        billAdapter.setBillList(billList);
                        gvBill.setAdapter(billAdapter);
                    }

                    @Override
                    public void onFailure(Call<BillResponse> call, Throwable t) {
                        Toast.makeText(ViewBillActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}