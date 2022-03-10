package com.example.grocerystoreowner.model.brand;

import android.icu.lang.UProperty;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BrandResponse {
    @Override
    public String toString() {
        return name;
    }
    private int id;
    private String name;
    private int status;
    private List<UserResponse> userList;
    private List<StoreResponse> storeList;
}
