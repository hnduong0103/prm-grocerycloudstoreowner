package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.category.CategoryResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryAPI {
    @GET("api/v1.0/categories")
    Call<CategoryResponse> getCategoryList(@Query("brand-id") int brandId,
                                           @Query("page-index") int pageIndex,
                                           @Query("page-size") int pageSize);
}
