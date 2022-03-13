package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.product.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("api/v1.0/products")
    Call<ProductResponse> getProductList(@Query("brand-id") int brandId,
                                         @Query("search-term") String searchTerm,
                                         @Query("category-id") Integer categoryId,
                                         @Query("include-disabled-product") boolean isDisabledInclude,
                                         @Query("page-index") int pageIndex,
                                         @Query("page-size") int pageSize);
}
