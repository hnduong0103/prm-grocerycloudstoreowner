package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.product.StockResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StockAPI {
    @GET("api/v1.0/stocks")
    Call<StockResponse> getStock(@Query("brand-id") int brandid,
                                 @Query("store-id") int storeid,
                                 @Query("search-term") String searchTerm,
                                 @Query("page-size") int pagesize);
}
