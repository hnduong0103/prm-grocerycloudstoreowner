package com.example.grocerystoreowner.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.model.product.ProductResponse;
import com.example.grocerystoreowner.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProductActivity extends AppCompatActivity {

    private EditText edtSearchTerm;
    private ListView lvProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        edtSearchTerm = findViewById(R.id.edt_search_term);
        lvProducts = findViewById(R.id.lv_products);

        Button btnSearch = findViewById(R.id.btn_search_products);
        btnSearch.setOnClickListener(view -> clickToSearchProducts());
    }

    public void clickToSearchProducts() {
        String searchTerm = edtSearchTerm.getText().toString();
        // TODO: add brand ID and category ID
        ProductService
                .getApi()
                .getProductList(1, searchTerm, null, false, 1, 10000)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                        Toast.makeText(ViewProductActivity.this, "Call API successfully", Toast.LENGTH_SHORT).show();
                        ProductResponse productList = response.body();
                        if (productList != null) {
                            List<String> renderItem = new ArrayList<>();
                            for (Product item : productList.getData()) {
                                renderItem.add(item.toString());
                            }
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewProductActivity.this, android.R.layout.simple_list_item_1, renderItem);
                            lvProducts.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                        Toast.makeText(ViewProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
