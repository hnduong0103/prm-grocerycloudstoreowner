package com.example.grocerystoreowner.model.receipt;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReceiptData {
    private int id;
    private int storeId;
    private String dateCreated;
    private int totalCost;
    private List<ReceiptDetail> receiptDetails;
}
