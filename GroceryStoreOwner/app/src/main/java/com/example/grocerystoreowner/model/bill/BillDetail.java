package com.example.grocerystoreowner.model.bill;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BillDetail {
    private String productName;
    private int productId;
    private int sellPrice;
    private int quantity;
}
