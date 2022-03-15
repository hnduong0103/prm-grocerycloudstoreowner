package com.example.grocerystoreowner.util;

import android.content.Context;

import com.example.grocerystoreowner.model.pendingitem.PendingItem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataLocalManager {

    private static final String PREF_OBJECT_PENDING_LIST = "PREF_OBJECT_PENDING_LIST";
    private static DataLocalManager instance;
    private MySharedPreferences mySharedPreferences;

    public static void init(Context context){
        instance = new DataLocalManager();
        instance.mySharedPreferences = new MySharedPreferences(context);
    }

    public static DataLocalManager getInstance(){
        if (instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setPendingList(List<PendingItem> listItem){
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(listItem).getAsJsonArray();
        String strJsonArray = jsonArray.toString();
        DataLocalManager.getInstance().mySharedPreferences.putStringValue(PREF_OBJECT_PENDING_LIST, strJsonArray);
    }

    public static List<PendingItem> getPendingList(){
        String strJsonArray = DataLocalManager.getInstance().mySharedPreferences.getStringValue(PREF_OBJECT_PENDING_LIST);
        List<PendingItem> listItem = new ArrayList<>();
        try{
            JSONArray jsonArray = new JSONArray(strJsonArray);
            JSONObject jsonObject;
            PendingItem item;
            Gson gson = new Gson();
            for (int i = 0; i< jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                item = gson.fromJson(jsonObject.toString(), PendingItem.class);
                listItem.add(item);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return listItem;
    }
}
