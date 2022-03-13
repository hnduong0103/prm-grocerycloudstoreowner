package com.example.grocerystoreowner.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventResponseList;
import com.example.grocerystoreowner.model.event.EventStatusPutRequest;
import com.example.grocerystoreowner.service.EventService;
import com.example.grocerystoreowner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {
    private GridView gridViewEvent;
    private TextView currentEventName;
    private EditText edtSearch;
    private Button unapplyBtn, findBtn;
    EventAdapter eventAdapter = new EventAdapter(EventListActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        gridViewEvent = findViewById(R.id.gridViewEvent);
        currentEventName= findViewById(R.id.applyingEventName);
        unapplyBtn = findViewById(R.id.btnUnApply);
        findBtn = findViewById(R.id.findBtn);
        edtSearch = findViewById(R.id.edtSearch);
    }
    public void fetchEventData(boolean refereshCurrentApplying, String searchTerm, int pageIndex, int pageSize) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        EventService.getApi().getEventList(brandId,searchTerm,pageIndex,pageSize)
                .enqueue(new Callback<EventResponseList>() {
                    @Override
                    public void onResponse(Call<EventResponseList> call, Response<EventResponseList> response) {
                        Toast.makeText(EventListActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        List<EventResponse> eventList = response.body().getData();
                        eventAdapter.setEventList(eventList);
                        gridViewEvent.setAdapter(eventAdapter);
                        EventResponse applyingEvent = null;
                        for (EventResponse event : eventList) {
                            if (event.getStatus()==0) {
                                applyingEvent = event;
                            }
                        }
                        final EventResponse applyingEventFinal = applyingEvent;
                        if (refereshCurrentApplying)  {
                            if (applyingEvent==null) {
                                currentEventName.setText("Không có sự kiện nào được áp dụng");
                                unapplyBtn.setVisibility(View.GONE);
                            } else {
                                currentEventName.setText(applyingEvent.getEventName());
                                unapplyBtn.setVisibility(View.VISIBLE);
                            }
                        }
                        //set listerer cho btn
                        unapplyBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                EventStatusPutRequest request = new EventStatusPutRequest();
                                request.setStatus(1);
                                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
                                Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
                                request.setBrandId(brandId);
                                EventService.getApi().changeEventStatus(applyingEventFinal.getId(),request)
                                        .enqueue(new Callback<Void>() {
                                            @Override
                                            public void onResponse(Call<Void> call, Response<Void> response) {
                                                fetchEventData(true,"",1,999);
                                            }

                                            @Override
                                            public void onFailure(Call<Void> call, Throwable t) {
                                                Toast.makeText(view.getContext(), "Apply event failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<EventResponseList> call, Throwable t) {
                        Toast.makeText(EventListActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchEventData(true,"",1,999);
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchEventData(false,edtSearch.getText().toString(),1,9999);
            }
        });
    }
}