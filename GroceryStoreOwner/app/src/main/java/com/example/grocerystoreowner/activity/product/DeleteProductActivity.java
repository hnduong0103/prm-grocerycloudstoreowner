package com.example.grocerystoreowner.activity.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.product.Product;
import com.example.grocerystoreowner.service.ProductService;
import com.example.grocerystoreowner.util.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteProductActivity extends AppCompatActivity {

    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_product);

        TextView txtSku = findViewById(R.id.txt_product_sku);
        TextView txtName = findViewById(R.id.txt_product_name);
        TextView txtCategory = findViewById(R.id.txt_product_category);
        TextView txtSellPrice = findViewById(R.id.txt_product_sell_price);
        TextView txtUnpackedProduct = findViewById(R.id.txt_product_unpacked_product);
        TextView txtConversionRate = findViewById(R.id.txt_product_conversion_rate);
        TextView txtUnitLabel = findViewById(R.id.txt_product_unit_label);
        TextView txtLowerThreshold = findViewById(R.id.txt_product_lower_threshold);
        TextView txtStatus = findViewById(R.id.txt_product_status);

        Intent intent = this.getIntent();
        product = (Product) intent.getSerializableExtra("product");

        txtSku.setText(product.getSku());
        txtName.setText(product.getName());
        txtCategory.setText(product.getCategoryName());
        txtSellPrice.setText(String.valueOf(product.getSellPrice()));
        txtUnpackedProduct.setText(product.getUnpackedProductName());
        txtConversionRate.setText(String.valueOf(product.getConversionRate()));
        txtUnitLabel.setText(product.getUnitLabel());
        txtLowerThreshold.setText(String.valueOf(product.getLowerThreshold()));
        txtStatus.setText(Constants.PRODUCT_STATUS[product.getStatus()]);

        Button btnConfirm = findViewById(R.id.btn_confirm_delete_product);
        btnConfirm.setOnClickListener(view -> clickToDelete());

        Button btnCancel = findViewById(R.id.btn_cancel_delete_product);
        btnCancel.setOnClickListener(view -> clickToCancel());
    }

    public void clickToDelete() {
        ProductService
                .getApi()
                // TODO: add brand ID
                .deleteProduct(product.getId(), 1)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(DeleteProductActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DeleteProductActivity.this, "Sản phẩm đang là sản phẩm đơn vị. Vui lòng xóa các sản phẩm lớn trước.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                        Toast.makeText(DeleteProductActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        finish();
    }

    public void clickToCancel() {
        finish();
    }
}