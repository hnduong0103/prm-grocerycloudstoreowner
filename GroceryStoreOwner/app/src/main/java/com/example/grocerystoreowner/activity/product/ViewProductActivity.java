package com.example.grocerystoreowner.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Button btnCreate = findViewById(R.id.btn_create_product);
        btnCreate.setOnClickListener(view -> clickToCreateProduct());
    }

    @Override
    protected void onResume() {
        super.onResume();
        clickToSearchProducts();
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
                        ProductResponse productList = response.body();
                        if (productList != null) {
                            ArrayAdapter<Product> adapter = new ArrayAdapter<>(ViewProductActivity.this, android.R.layout.simple_list_item_1, productList.getData());
                            lvProducts.setAdapter(adapter);
                            lvProducts.setOnItemClickListener((adapterView, view, i, l) -> {
                                Product product = (Product) lvProducts.getItemAtPosition(i);
                                Intent intent = new Intent(ViewProductActivity.this, DetailProductActivity.class);
                                intent.putExtra("product", product);
                                startActivity(intent);
                            });
                            lvProducts.setOnItemLongClickListener((adapterView, view, i, l) -> {
                                Product product = (Product) lvProducts.getItemAtPosition(i);
                                Intent intent = new Intent(ViewProductActivity.this, DeleteProductActivity.class);
                                intent.putExtra("product", product);
                                startActivity(intent);
                                return true;
                            });
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                        Toast.makeText(ViewProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void clickToCreateProduct() {
        Intent intent = new Intent(ViewProductActivity.this, DetailProductActivity.class);
        startActivity(intent);
    }
}
