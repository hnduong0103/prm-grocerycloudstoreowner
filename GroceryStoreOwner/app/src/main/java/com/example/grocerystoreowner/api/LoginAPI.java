package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.bill.BillResponse;
import com.example.grocerystoreowner.model.login.LoginRequest;
import com.example.grocerystoreowner.model.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LoginAPI {
    @POST("api/v1.0/login")
    Call<LoginResponse> login(@Body LoginRequest request);
}
