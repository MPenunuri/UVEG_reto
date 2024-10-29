package com.retouveg;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        String productName = getIntent().getStringExtra("PRODUCT_NAME");
        double productPrice = getIntent().getDoubleExtra("PRODUCT_PRICE", -1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(productName);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextView nameView = findViewById(R.id.productNameInProductActivity);
        nameView.setText(productName);

        TextView priceView = findViewById(R.id.productPriceInProductActivity);
        Locale locale = new Locale("es", "MX");
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
        String formatPrice = currencyFormat.format(new BigDecimal(productPrice));
        priceView.setText(formatPrice);
    }
}