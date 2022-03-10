package com.example.grocerystoreowner.model.brand;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StoreResponse {
    private int id;
    private int brandId;
    private String brandName;
    private String name;
    private String address;
    private int approvedStatus;
}
