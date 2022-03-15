package com.example.grocerystoreowner.model.receipt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReceiptCreateItem {
    private int productId;
    private int buyPrice;
    private int quantity;

    public ReceiptCreateItem(int productId, int buyPrice, int quantity) {
        this.productId = productId;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
    }
}
