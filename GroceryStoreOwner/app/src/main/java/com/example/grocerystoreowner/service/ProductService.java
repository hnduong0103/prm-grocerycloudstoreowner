package com.example.grocerystoreowner.service;

import com.example.grocerystoreowner.api.ProductAPI;
import com.example.grocerystoreowner.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductService {
    private static ProductAPI api;

    public static synchronized ProductAPI getApi() {
        if (api == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            api = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ProductAPI.class);
        }
        return api;
    }
}
