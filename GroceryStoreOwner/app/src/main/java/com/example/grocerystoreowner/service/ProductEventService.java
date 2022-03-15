package com.example.grocerystoreowner.service;

import com.example.grocerystoreowner.api.EventAPI;
import com.example.grocerystoreowner.api.ProductEventAPI;
import com.example.grocerystoreowner.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductEventService
{
    private static ProductEventAPI api;

    public static synchronized ProductEventAPI getApi() {
        if (api == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            api = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ProductEventAPI.class);
        }
        return api;
    }
}
