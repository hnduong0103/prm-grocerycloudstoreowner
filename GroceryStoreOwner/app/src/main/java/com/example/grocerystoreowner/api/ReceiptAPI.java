package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.model.receipt.ReceiptResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ReceiptAPI {
    @GET("api/v1.0/receipts")
    Call<ReceiptResponse> getReceiptList(@Query("store-id") int storeId,
                                        @Query("start-date") String startDate,
                                        @Query("end-date") String endDate);
}
