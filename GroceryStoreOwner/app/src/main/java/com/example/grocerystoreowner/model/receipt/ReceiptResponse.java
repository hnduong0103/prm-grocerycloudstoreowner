package com.example.grocerystoreowner.model.receipt;

import com.example.grocerystoreowner.model.bill.BillData;
import com.example.grocerystoreowner.model.common.Pagination;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Data
@Getter
@Setter
public class ReceiptResponse extends Pagination {
    private List<ReceiptData> data;
}
