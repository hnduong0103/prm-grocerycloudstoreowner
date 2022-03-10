package com.example.grocerystoreowner.model.event;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EventResponse {
    private int id;
    private String eventName;
    private int status;
    private int productCount;
    private List<EventDetailResponse> eventDetails;
}
