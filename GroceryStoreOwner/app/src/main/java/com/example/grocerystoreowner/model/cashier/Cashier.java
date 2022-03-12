package com.example.grocerystoreowner.model.cashier;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Cashier {
    private int id;
    private String name;
    private int storeId;
    private String storeName;
    private String username;
    private int status;
}