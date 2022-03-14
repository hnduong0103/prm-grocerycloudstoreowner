package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.model.product.ProductResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProductAPI {
    @GET("api/v1.0/products")
    Call<ProductResponse> getProductList(@Query("brand-id") int brandId,
                                         @Query("search-term") String searchTerm,
                                         @Query("category-id") Integer categoryId,
                                         @Query("include-disabled-product") boolean isDisabledInclude,
                                         @Query("page-index") int pageIndex,
                                         @Query("page-size") int pageSize);

    @POST("api/v1.0/products")
    Call<Void> createProduct(@Query("brand-id") int brandId,
                             @Body Product product);

    @PUT("api/v1.0/products/{id}")
    Call<Void> updateProduct(@Path("id") int id,
                             @Query("brand-id") int brandId,
                             @Body Product product);

    @DELETE("api/v1.0/products/{id}")
    Call<Void> deleteProduct(@Path("id") int id,
                             @Query("brand-id") int brandId);
}
