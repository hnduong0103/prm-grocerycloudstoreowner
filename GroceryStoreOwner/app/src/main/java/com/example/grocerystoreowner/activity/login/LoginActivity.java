package com.example.grocerystoreowner.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.brand.SwitchBrandActivity;
import com.example.grocerystoreowner.model.login.LoginRequest;
import com.example.grocerystoreowner.model.login.LoginResponse;
import com.example.grocerystoreowner.service.LoginService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void clickToLogin(View view) {
        String txtUsername = username.getText().toString();
        String txPtassword = password.getText().toString();
        LoginService.getApi().login(new LoginRequest(txtUsername,txPtassword))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Toast.makeText(LoginActivity.this, "Call API success: user Id: "+response.body().getInformation().getId(), Toast.LENGTH_SHORT).show();

                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
                        SharedPreferenceUtil.putKey(sharedPreferences, Constants.USER_ID_SHARED_PREFERENCE, new Integer(response.body().getInformation().getId()).toString());
                        SharedPreferenceUtil.putKey(sharedPreferences, "HasProductToAdd", "0");

                        Intent intent = new Intent(LoginActivity.this, SwitchBrandActivity.class);
                        startActivity(intent);
                        //Toast.makeText(LoginActivity.this, "SP success: user Id: "+sharedPreferences.getString("Name",null), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}