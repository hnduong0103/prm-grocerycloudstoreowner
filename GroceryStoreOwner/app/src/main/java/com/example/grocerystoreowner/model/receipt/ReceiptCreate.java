package com.example.grocerystoreowner.model.receipt;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReceiptCreate {
    private int storeId;
    private int totalCost;
    private List<ReceiptCreateItem> details;
}
