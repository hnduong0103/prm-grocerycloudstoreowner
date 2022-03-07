package com.example.grocerystoreowner.model.common;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data @Getter @Setter
public class Pagination {
    private int pageIndex;
    private int pageSize;
    private int totalItem;
    private int totalPage;
}
