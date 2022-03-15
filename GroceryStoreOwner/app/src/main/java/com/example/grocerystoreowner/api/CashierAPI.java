package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.cashier.Cashier;
import com.example.grocerystoreowner.model.cashier.CashierResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CashierAPI {
    @GET("api/v1.0/cashiers?page-size=10000")
    Call<CashierResponse> getCashierList(@Query("brand-id") int brandId,
                                         @Query("store-id") Integer storeId,
                                         @Query("search-term") String searchTerm,
                                         @Query("include-disabled-cashier") boolean isDisabledInclude);

    @POST("api/v1.0/cashiers")
    Call<Void> createCashier(@Body Cashier cashier);

    @PUT("api/v1.0/cashiers/{id}")
    Call<Void> updateCashier(@Path("id") int id,
                             @Body Cashier cashier);

    @DELETE("api/v1.0/cashiers/{id}")
    Call<Void> deleteCashier(@Path("id") int id);
}
