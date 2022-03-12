package com.example.grocerystoreowner.model.receipt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReceiptDetail {
    private int receiptId;
    private int productId;
    private String productName;
    private int buyPrice;
    private int quantity;
}
