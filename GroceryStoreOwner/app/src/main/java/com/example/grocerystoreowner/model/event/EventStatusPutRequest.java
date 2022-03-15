package com.example.grocerystoreowner.model.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class EventStatusPutRequest {
    private int brandId;
    private int status;
}
