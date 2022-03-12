package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.product.ProductResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CashierAPI {
    @GET("api/v1.0/products?page-size=10000")
    Call<List<ProductResponse>> getCashierList(@Query("brand-id") int brandId,
                                               @Query("store-id") int storeId,
                                               @Query("search-term") String searchTerm,
                                               @Query("include-disabled-cashier") boolean isDisabledInclude);
}
