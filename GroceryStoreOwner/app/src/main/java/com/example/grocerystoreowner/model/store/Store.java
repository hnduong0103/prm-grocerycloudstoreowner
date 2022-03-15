package com.example.grocerystoreowner.model.store;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Store {
    @Override
    public String toString() {
        return name;
    }
    private int id;
    private int brandId;
    private String brandName;
    private String name;
    private String address;
    private int approvedStatus;
}
