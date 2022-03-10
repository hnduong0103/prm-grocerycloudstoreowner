package com.example.grocerystoreowner.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventResponseList;
import com.example.grocerystoreowner.service.EventService;
import com.example.grocerystoreowner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {
    private GridView gridViewEvent;
    EventAdapter eventAdapter = new EventAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        gridViewEvent = findViewById(R.id.gridViewEvent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        EventService.getApi().getEventList(brandId,"",1,9999)
                .enqueue(new Callback<EventResponseList>() {
                    @Override
                    public void onResponse(Call<EventResponseList> call, Response<EventResponseList> response) {
                        Toast.makeText(EventListActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        List<EventResponse> eventList = response.body().getData();
                        eventAdapter.setEventList(eventList);
                        gridViewEvent.setAdapter(eventAdapter);
                    }

                    @Override
                    public void onFailure(Call<EventResponseList> call, Throwable t) {
                        Toast.makeText(EventListActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}