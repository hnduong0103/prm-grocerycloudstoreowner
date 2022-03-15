package com.example.grocerystoreowner.model.event;

import com.example.grocerystoreowner.model.common.Pagination;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ProductSearchInEventResponseList extends Pagination {
    private List<ProductSearchInEventResponse> data;
}
