package com.example.grocerystoreowner.model.product;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StockData {
    private int id;
    private int categoryId;
    private String name;
    private String unitLabel;
    private int currentQuantity;
}
