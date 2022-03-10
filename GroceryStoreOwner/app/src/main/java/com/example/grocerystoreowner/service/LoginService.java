package com.example.grocerystoreowner.service;

import com.example.grocerystoreowner.api.BillAPI;
import com.example.grocerystoreowner.api.LoginAPI;
import com.example.grocerystoreowner.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginService {
    private static LoginAPI api;

    public static synchronized LoginAPI getApi() {
        if (api == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            api = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                    .create(LoginAPI.class);
        }
        return api;
    }
}
