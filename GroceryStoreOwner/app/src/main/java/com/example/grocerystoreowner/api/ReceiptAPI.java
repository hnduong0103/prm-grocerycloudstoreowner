package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.model.receipt.ReceiptCreate;
import com.example.grocerystoreowner.model.receipt.ReceiptResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ReceiptAPI {
    @GET("api/v1.0/receipts")
    Call<ReceiptResponse> getReceiptList(@Query("store-id") int storeId,
                                        @Query("start-date") String startDate,
                                        @Query("end-date") String endDate,
                                         @Query("page-size") int pagesize);

    @POST("api/v1.0/receipts")
    Call<ReceiptCreate> createReceipt(@Body ReceiptCreate receipt);
}
