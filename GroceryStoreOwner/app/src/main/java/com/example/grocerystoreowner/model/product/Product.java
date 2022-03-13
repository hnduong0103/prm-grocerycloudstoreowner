package com.example.grocerystoreowner.model.product;

import androidx.annotation.NonNull;

import com.example.grocerystoreowner.util.Constants;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Product implements Serializable {
    private int id;
    private String name;
    private int unpackedProductId;
    private String unpackedProductName;
    private int sellPrice;
    private int categoryId;
    private String categoryName;
    private int conversionRate;
    private String unitLabel;
    private int lowerThreshold;
    private int status;
    private String sku;

    @NonNull
    @Override
    public String toString() {
        return "Mã sản phẩm: " + sku + '\n' +
                "Tên sản phẩm: " + name + '\n' +
                "Danh mục: " + categoryName + '\n' +
                "Giá bán: " + sellPrice + '\n' +
                "Đơn vị tính: " + unitLabel + '\n' +
                "Trạng thái: " + Constants.PRODUCT_STATUS[status] + '\n';
    }
}