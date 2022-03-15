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
import com.example.grocerystoreowner.activity.brand.SwitchBrandActivity;
import com.example.grocerystoreowner.activity.login.LoginActivity;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventStatusPutRequest;
import com.example.grocerystoreowner.service.EventService;
import com.example.grocerystoreowner.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAdapter extends BaseAdapter implements ListAdapter {
    public EventAdapter(EventListActivity activity) {
        _activity = activity;
    }

    public void setEventList(List<EventResponse> _eventList) {
        this._eventList = _eventList;
    }
    private EventListActivity _activity;
    private List<EventResponse> _eventList;

    @Override
    public int getCount() {
        return _eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return _eventList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _eventList.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.event_grid_item, viewGroup, false);
        }
        TextView eventName = view.findViewById(R.id.eventName);
        EventResponse event = _eventList.get(i);
        eventName.setText(event.getEventName());
        eventName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity và truyền vào id
                Intent intent = new Intent(view.getContext(), EventDetailActivity.class);
                intent.putExtra("EventId", event.getId());
                view.getContext().startActivity(intent);

            }
        });
        Button applyBtn = view.findViewById(R.id.btnApply);
        applyBtn.setEnabled(event.getStatus()==1);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Select event: "+event.getEventName()+ " ID:" + new Integer(event.getId()).toString(), Toast.LENGTH_SHORT).show();
                EventStatusPutRequest request = new EventStatusPutRequest();
                request.setStatus(0);
                SharedPreferences sharedPreferences = view.getContext().getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
                Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
                request.setBrandId(brandId);
                EventService.getApi().changeEventStatus(event.getId(),request)
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(view.getContext(), "Apply event "+event.getEventName()+" successfully", Toast.LENGTH_SHORT).show();
                                _activity.fetchEventData(true,"",1,999);
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(view.getContext(), "Apply event failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return view;
    }
}
