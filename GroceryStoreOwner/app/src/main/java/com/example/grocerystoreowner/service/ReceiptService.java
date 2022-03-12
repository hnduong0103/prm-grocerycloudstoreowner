package com.example.grocerystoreowner.service;

import com.example.grocerystoreowner.api.BillAPI;
import com.example.grocerystoreowner.api.ReceiptAPI;
import com.example.grocerystoreowner.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReceiptService {
    private static ReceiptAPI api;
    public static synchronized ReceiptAPI getApi() {
        if (api == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            api = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(ReceiptAPI.class);
        }
        return api;
    }
}
