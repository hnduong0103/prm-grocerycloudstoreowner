package com.example.grocerystoreowner.api;

import com.example.grocerystoreowner.model.store.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StoreAPI {
    @GET("api/v1.0/stores")
    Call<List<Store>> getStoreList(@Query("brand-id") int brandId);
}
