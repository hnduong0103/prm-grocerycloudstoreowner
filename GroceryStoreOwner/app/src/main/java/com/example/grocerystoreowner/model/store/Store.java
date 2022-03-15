package com.example.grocerystoreowner.model.store;

import lombok.Data;

@Data
public class Store {
    private int id;
    private int brandId;
    private String brandName;
    private String name;
    private String address;
    private int approvedStatus;
}
