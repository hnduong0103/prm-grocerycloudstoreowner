package com.example.grocerystoreowner.model.bill;

import com.example.grocerystoreowner.model.common.Pagination;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class BillResponse extends Pagination {
    private List<BillData> data;
}
