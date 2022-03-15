package com.example.grocerystoreowner.util;

import android.app.Application;

public class StoreOwnerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
