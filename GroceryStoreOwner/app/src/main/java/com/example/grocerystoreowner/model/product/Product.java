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
    private Integer unpackedProductId;
    private String unpackedProductName;
    private int sellPrice;
    private int categoryId;
    private String categoryName;
    private int conversionRate;
    private String unitLabel;
    private int lowerThreshold;
    private int status;
    private String sku;

    public Product(String name, Integer unpackedProductId, int sellPrice, int categoryId, int conversionRate, String unitLabel, int lowerThreshold, int status, String sku) {
        this.name = name;
        this.unpackedProductId = unpackedProductId;
        this.sellPrice = sellPrice;
        this.categoryId = categoryId;
        this.conversionRate = conversionRate;
        this.unitLabel = unitLabel;
        this.lowerThreshold = lowerThreshold;
        this.status = status;
        this.sku = sku;
    }
}