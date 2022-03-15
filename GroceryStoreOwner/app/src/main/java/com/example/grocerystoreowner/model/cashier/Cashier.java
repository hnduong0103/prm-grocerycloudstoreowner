package com.example.grocerystoreowner.model.cashier;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Cashier implements Serializable {
    private int id;
    private String name;
    private int storeId;
    private String storeName;
    private String username;
    private int status;
    private String password;

    public Cashier(String name, int storeId, String username, String password) {
        this.name = name;
        this.storeId = storeId;
        this.username = username;
        this.password = password;
    }
}