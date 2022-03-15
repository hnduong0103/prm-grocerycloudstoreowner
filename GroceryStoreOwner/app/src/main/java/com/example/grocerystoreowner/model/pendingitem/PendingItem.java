package com.example.grocerystoreowner.model.pendingitem;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PendingItem {
    private int ProductId;
    private String ProductName;
    private int Quantity;
    private int BuyPrice;

    public PendingItem(int productId, String productName, int quantity, int buyPrice){
        this.ProductId = productId;
        this.ProductName = productName;
        this.Quantity = quantity;
        this.BuyPrice = buyPrice;
    }
}
