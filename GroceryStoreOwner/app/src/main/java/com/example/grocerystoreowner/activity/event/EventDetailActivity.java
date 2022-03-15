package com.example.grocerystoreowner.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.activity.brand.SwitchBrandActivity;
import com.example.grocerystoreowner.activity.login.LoginActivity;
import com.example.grocerystoreowner.model.event.EventDetailResponse;
import com.example.grocerystoreowner.model.event.EventResponse;
import com.example.grocerystoreowner.model.event.EventResponseList;
import com.example.grocerystoreowner.model.event.EventStatusPutRequest;
import com.example.grocerystoreowner.model.event.ProductSearchInEventResponse;
import com.example.grocerystoreowner.service.EventService;
import com.example.grocerystoreowner.util.Constants;
import com.example.grocerystoreowner.util.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailActivity extends AppCompatActivity {

    private EditText edtEventName;
    private TextView txtBrand;
    private GridView gridViewProduct;
    private Button addBtn;
    private ProductAdapter productAdapter = new ProductAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        edtEventName = findViewById(R.id.edtEventName);
        txtBrand= findViewById(R.id.txtBrand);
        gridViewProduct = findViewById(R.id.gridViewProduct);
        addBtn = findViewById(R.id.addBtn);
        Bundle extras = getIntent().getExtras();
        int eventId = 0;
        if(extras != null) {
            eventId = extras.getInt("EventId");
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
            Integer brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
            String brandName =sharedPreferences.getString(Constants.BRAND_NAME_SHARED_PREFERENCE,null);

            EventService.getApi().getEventById(eventId,brandId)
                    .enqueue(new Callback<EventResponse>() {
                        @Override
                        public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                            Toast.makeText(EventDetailActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                            EventResponse event = response.body();
                            productAdapter.setEventDetailList(event.getEventDetails());
                            gridViewProduct.setAdapter(productAdapter);
                            edtEventName.setText(event.getEventName());
                            txtBrand.setText(brandName);

                            //set listerer cho btn
                            addBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(EventDetailActivity.this, "Btn clicked!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(EventDetailActivity.this, ProductSearchActivity.class);
                                    startActivity(intent);
                                }
                            });

                        }

                        @Override
                        public void onFailure(Call<EventResponse> call, Throwable t) {
                            Toast.makeText(EventDetailActivity.this, "Call API failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        //xet xem neu co san pham de add vo?
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Integer hasProductToAdd = Integer.parseInt(sharedPreferences.getString("HasProductToAdd",null));
        if (hasProductToAdd==1) {
            ProductSearchInEventResponse product = new Gson().fromJson(sharedPreferences.getString("ProductToAddToEvent",null)
                    , ProductSearchInEventResponse.class);
            List<EventDetailResponse> detailList = productAdapter.getEventDetailList();
            EventDetailResponse newEventDetail = new EventDetailResponse();
            newEventDetail.setProductId(product.getId());
            newEventDetail.setProductName(product.getName());
            newEventDetail.setOriginalPrice(product.getSellPrice());
            newEventDetail.setNewPrice(product.getSellPrice());
            detailList.add(newEventDetail);
            productAdapter.setEventDetailList(detailList);
            SharedPreferenceUtil.putKey(sharedPreferences, "HasProductToAdd", "0");
        }

    }
}