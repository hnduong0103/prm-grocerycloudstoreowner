package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.billmodel.BillResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BillAPIService {
    //GetBills: https://grocerycloudstoreownerapi2.azurewebsites.net/api/v1.0/bills?store-id=1&start-date=02-02-2020&end-date=02-02-2022

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    BillAPIService billApiService = new Retrofit.Builder()
            .baseUrl("https://grocerycloudstoreownerapi2.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(BillAPIService.class);

    @GET("api/v1.0/bills")
    Call<BillResponse> getBillList(@Query("store-id") int storeid,
                                   @Query("start-date") String startdate,
                                   @Query("end-date") String enddate);

}
