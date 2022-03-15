package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.event.EventResponseList;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponse;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponseList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductEventAPI {
    @GET("api/v1.0/products")
    Call<ProductSearchInEventResponseList> getProductlist(@Query("brand-id") int brandId,
                                                          @Query("search-term") String searchTerm,
                                                          @Query("page-index") int pageIndex,
                                                          @Query("page-size") int pageSize
    );
    @GET("api/v1.0/products")
    Call<ProductSearchInEventResponse> getProductlistWithCategory(@Query("brand-id") int brandId,
                                                                  @Query("category-id") String categoryId,
                                                      @Query("search-term") String searchTerm,
                                                      @Query("page-index") int pageIndex,
                                                      @Query("page-size") int pageSize
    );
}
