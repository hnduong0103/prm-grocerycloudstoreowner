package com.example.grocerystoreowner.model.bill;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class BillData {
    private int id;
    private int storeId;
    private int cashierId;
    private String cashierName;
    private String dateCreated;
    private int totalPrice;
}
