package com.example.grocerystoreowner.activity.product;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.grocerystoreowner.R;
import com.example.grocerystoreowner.model.product.Product;

public class DetailProductActivity extends AppCompatActivity {

    private Button btnConfirm, btnCancel;
    private EditText edtSKU, edtName, edtSellPrice, edtConversionRate, edtUnitLabel, edtLowerThreshold;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        btnConfirm = findViewById(R.id.btn_confirm_update_product);
        btnCancel = findViewById(R.id.btn_cancel_update_product);
        btnConfirm.setOnClickListener(view -> clickToUpdate());
        btnCancel.setOnClickListener(view -> clickToCancel());

        Intent intent = this.getIntent();
        product = (Product) intent.getSerializableExtra("product");

        edtSKU = findViewById(R.id.edt_product_sku);
        edtName = findViewById(R.id.edt_product_name);
        edtSellPrice = findViewById(R.id.edt_product_sell_price);
        edtConversionRate = findViewById(R.id.edt_product_conversion_rate);
        edtUnitLabel = findViewById(R.id.edt_product_unit_label);
        edtLowerThreshold = findViewById(R.id.edt_product_lower_threshold);

        edtSKU.setText(product.getSku());
        edtName.setText(product.getName());
        edtSellPrice.setText(String.valueOf(product.getSellPrice()));
        edtConversionRate.setText(String.valueOf(product.getConversionRate()));
        edtUnitLabel.setText(product.getUnitLabel());
        edtLowerThreshold.setText(String.valueOf(product.getLowerThreshold()));
    }

    public void clickToUpdate() {
        finish();
    }

    public void clickToCancel() {
        finish();
    }
}