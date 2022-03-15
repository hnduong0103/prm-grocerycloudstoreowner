package com.example.grocerystoreowner.model.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductSearchInEventResponse {
    private int id;
    private String name;
    private int sellPrice;
    private String categoryName;
    private String unitLabel;
    private String sku;
}
