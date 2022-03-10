package com.example.grocerystoreowner.model.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EventDetailResponse {
    private int eventId;
    private int productId;
    private String productName;
    private int newPrice;
    private int originalPrice;
    private String sku;
}
