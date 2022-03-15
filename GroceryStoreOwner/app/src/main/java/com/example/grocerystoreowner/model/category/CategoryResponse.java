package com.example.grocerystoreowner.model.category;

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
public class CategoryResponse extends Pagination {
    private List<Category> data;
}
