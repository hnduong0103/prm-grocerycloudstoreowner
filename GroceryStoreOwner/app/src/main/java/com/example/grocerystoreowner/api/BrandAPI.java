package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.brand.BrandResponse;
import com.example.grocerystoreowner.model.login.LoginRequest;
import com.example.grocerystoreowner.model.login.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BrandAPI {
    @GET("api/v1.0/brands")
    Call<List<BrandResponse>> getBrands(@Query("user-id") int userId);
}
