package com.example.grocerystoreowner.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.adapter.ProductAdapter;
import com.example.grocerystoreowner.model.category.Category;
import com.example.grocerystoreowner.model.category.CategoryResponse;
import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.model.product.ProductResponse;
import com.example.grocerystoreowner.service.CategoryService;
import com.example.grocerystoreowner.service.ProductService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewProductActivity extends AppCompatActivity {

    private EditText edtSearchTerm;
    private ListView lvProducts;
    private Spinner spnCategory;
    private List<Category> categoryList;
    private final String CATEGORY_ALL = "Tất cả";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);

        edtSearchTerm = findViewById(R.id.edt_search_term);
        lvProducts = findViewById(R.id.lv_products);
        spnCategory = findViewById(R.id.spn_categories_picker);


        Button btnSearch = findViewById(R.id.btn_search_products);
        btnSearch.setOnClickListener(view -> clickToSearchProducts());

        Button btnCreate = findViewById(R.id.btn_create_product);
        btnCreate.setOnClickListener(view -> clickToCreateProduct());

        fetchCategoryList();

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clickToSearchProducts();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        spnCategory.setSelection(0);
        clickToSearchProducts();
    }

    public void clickToSearchProducts() {
        Integer categoryId;
        if (spnCategory.getSelectedItem() == null) {
            categoryId = null;
        } else {
            categoryId = findCategoryIdByName(spnCategory.getSelectedItem().toString());
            if (categoryId == 0) {
                categoryId = null;
            }
        }

        String searchTerm = edtSearchTerm.getText().toString();
        // TODO: add brand ID
        ProductService
                .getApi()
                .getProductList(1, searchTerm, categoryId, false, 1, 10000)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                        ProductResponse productList = response.body();
                        if (productList != null) {
                            ProductAdapter adapter = new ProductAdapter();
                            adapter.setProductList(productList.getData());
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

    public void fetchCategoryList() {
        CategoryService
                .getApi()
                // TODO: add brand ID
                .getCategoryList(1, 1, 10000)
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                        if (response.body() != null) {
                            categoryList = response.body().getData();
                            List<String> categoryNameList = new ArrayList<>();
                            categoryNameList.add(CATEGORY_ALL);
                            for (Category category : categoryList) {
                                categoryNameList.add(category.getName());
                            }
                            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(ViewProductActivity.this, android.R.layout.simple_spinner_item, categoryNameList);
                            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spnCategory.setAdapter(categoryAdapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                        Toast.makeText(ViewProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public int findCategoryIdByName(String categoryName) {
        if (categoryName.equals(CATEGORY_ALL)) {
            return 0;
        }

        for (Category category : categoryList) {
            if (categoryName.equals(category.getName())) {
                return category.getId();
            }
        }
        return -1;
    }
}
