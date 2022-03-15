package com.example.grocerystoreowner.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.category.Category;
import com.example.grocerystoreowner.model.category.CategoryResponse;
import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.model.product.ProductResponse;
import com.example.grocerystoreowner.service.CategoryService;
import com.example.grocerystoreowner.service.ProductService;
import com.example.grocerystoreowner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {

    private EditText edtSKU, edtName, edtSellPrice, edtConversionRate, edtUnitLabel, edtLowerThreshold;
    private Product product;
    private AutoCompleteTextView spnCategory, spnProduct;
    private Spinner spnStatus;
    private List<Category> categoryList;
    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        Button btnConfirm = findViewById(R.id.btn_confirm_update_product);
        btnConfirm.setOnClickListener(view -> clickToUpdate());

        Button btnCancel = findViewById(R.id.btn_cancel_update_product);
        btnCancel.setOnClickListener(view -> clickToCancel());

        Intent intent = this.getIntent();
        product = (Product) intent.getSerializableExtra("product");

        edtSKU = findViewById(R.id.edt_product_sku);
        edtName = findViewById(R.id.edt_product_name);
        edtSellPrice = findViewById(R.id.edt_product_sell_price);
        edtConversionRate = findViewById(R.id.edt_product_conversion_rate);
        edtUnitLabel = findViewById(R.id.edt_product_unit_label);
        edtLowerThreshold = findViewById(R.id.edt_product_lower_threshold);
        spnCategory = findViewById(R.id.spn_product_category);
        spnProduct = findViewById(R.id.spn_product_unpacked_product);
        spnStatus = findViewById(R.id.spn_product_status);

        fetchCategoryList();
        fetchProductList();
        fetchStatusList();

        if (product != null) {
            edtSKU.setText(product.getSku());
            edtName.setText(product.getName());
            edtSellPrice.setText(String.valueOf(product.getSellPrice()));
            edtConversionRate.setText(String.valueOf(product.getConversionRate()));
            edtUnitLabel.setText(product.getUnitLabel());
            edtLowerThreshold.setText(String.valueOf(product.getLowerThreshold()));
            spnCategory.setText(product.getCategoryName());
            spnProduct.setText(product.getUnpackedProductName());
            spnStatus.setSelection(product.getStatus());
        }
    }

    public void clickToUpdate() {
        if (edtSellPrice.getText().toString().trim().isEmpty()) {
            notify("Vui lòng nhập giá bán.");
            return;
        }

        if (edtConversionRate.getText().toString().trim().isEmpty()) {
            notify("Vui lòng nhập tỉ lệ quy đổi.");
            return;
        }

        if (edtLowerThreshold.getText().toString().trim().isEmpty()) {
            notify("Vui lòng nhập ngưỡng hết hàng.");
            return;
        }

        String name = edtName.getText().toString().trim();
        int unpackedProductId = findProductIdByName(spnProduct.getText().toString().trim());
        int sellPrice = Integer.parseInt(edtSellPrice.getText().toString().trim());
        int categoryId = findCategoryIdByName(spnCategory.getText().toString().trim());
        int conversionRate = Integer.parseInt(edtConversionRate.getText().toString().trim());
        String unitLabel = edtUnitLabel.getText().toString().trim();
        int lowerThreshold = Integer.parseInt(edtLowerThreshold.getText().toString().trim());
        int status = spnStatus.getSelectedItemPosition();
        String sku = edtSKU.getText().toString().trim();

        if (sku.isEmpty()) {
            notify("Vui lòng nhập mã sản phẩm.");
            return;
        }

        if (name.isEmpty()) {
            notify("Vui lòng nhập tên sản phẩm.");
            return;
        }

        if (categoryId == -1) {
            notify("Danh mục không hợp lệ.");
            return;
        }

        if (unpackedProductId == -1 && !spnProduct.getText().toString().trim().isEmpty()) {
            notify("Sản phẩm đơn vị không hợp lệ.");
            return;
        }

        if (unitLabel.isEmpty()) {
            notify("Vui lòng nhập đơn vị tính.");
            return;
        }

        Product newProduct = new Product(
                name,
                unpackedProductId != -1 ? unpackedProductId : null,
                sellPrice,
                categoryId,
                conversionRate,
                unitLabel,
                lowerThreshold,
                status,
                sku
        );

        Call<Void> api;
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        int brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));

        if (product == null) {
            api = ProductService.getApi().createProduct(brandId, newProduct);
        } else {
            api = ProductService.getApi().updateProduct(product.getId(), brandId, newProduct);
        }

        api.enqueue(new Callback<Void>() {

            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Toast.makeText(DetailProductActivity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Toast.makeText(DetailProductActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        finish();
    }

    public void clickToCancel() {
        finish();
    }

    public void fetchCategoryList() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        int brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        CategoryService
                .getApi()
                .getCategoryList(brandId, 1, 10000)
                .enqueue(new Callback<CategoryResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<CategoryResponse> call, @NonNull Response<CategoryResponse> response) {
                        if (response.body() != null) {
                            categoryList = response.body().getData();
                            List<String> categoryNameList = new ArrayList<>();
                            for (Category category : categoryList) {
                                categoryNameList.add(category.getName());
                            }
                            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.select_dialog_item, categoryNameList);
                            spnCategory.setThreshold(1); //will start working from first character
                            spnCategory.setAdapter(categoryAdapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CategoryResponse> call, @NonNull Throwable t) {
                        Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void fetchProductList() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.GROCERY_CLOUD_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        int brandId = Integer.parseInt(sharedPreferences.getString(Constants.BRAND_ID_SHARED_PREFERENCE,null));
        ProductService
                .getApi()
                .getProductList(brandId, null, null, false, 1, 10000)
                .enqueue(new Callback<ProductResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                        if (response.body() != null) {
                            productList = response.body().getData();
                            List<String> productNameList = new ArrayList<>();
                            for (Product product : productList) {
                                productNameList.add(product.getName());
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.select_dialog_item, productNameList);
                            spnProduct.setThreshold(1); //will start working from first character
                            spnProduct.setAdapter(productAdapter);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                        Toast.makeText(DetailProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void fetchStatusList() {
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(DetailProductActivity.this, android.R.layout.simple_spinner_item, Constants.PRODUCT_STATUS);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnStatus.setAdapter(statusAdapter);
    }

    public int findCategoryIdByName(String categoryName) {
        for (Category category : categoryList) {
            if (categoryName.equals(category.getName())) {
                return category.getId();
            }
        }
        return -1;
    }

    public int findProductIdByName(String productName) {
        for (Product product : productList) {
            if (productName.equals(product.getName())) {
                return product.getId();
            }
        }
        return -1;
    }

    private void notify(String message) {
        Toast.makeText(DetailProductActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}