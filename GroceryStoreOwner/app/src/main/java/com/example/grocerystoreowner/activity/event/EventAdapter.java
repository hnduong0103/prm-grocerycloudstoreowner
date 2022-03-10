package com.example.grocerystoreowner.activity.event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.event.EventResponse;

import java.util.List;

public class EventAdapter extends BaseAdapter implements ListAdapter {
    public void setEventList(List<EventResponse> _eventList) {
        this._eventList = _eventList;
    }

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
        return view;
    }
}
