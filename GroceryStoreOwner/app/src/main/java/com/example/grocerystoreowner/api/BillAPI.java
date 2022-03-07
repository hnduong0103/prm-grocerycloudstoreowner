package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.bill.BillResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BillAPI {
    @GET("/bills")
    Call<BillResponse> getBillList(@Query("store-id") int storeId,
                                   @Query("start-date") String startDate,
                                   @Query("end-date") String endDate);
}
