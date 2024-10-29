package com.retouveg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;
import java.math.BigDecimal;

public class AddFood extends AppCompatActivity {
    private long id;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(v -> createFood());

        id = getIntent().getLongExtra("RESTAURANT_ID",-1);
        type = getIntent().getStringExtra("TYPE");

        TextView typeView = findViewById(R.id.editTextType);
        typeView.setText(type);
    }
    private void createFood() {
        TextView nameView = findViewById(R.id.editTextName);
        String registeredName = nameView.getText().toString();
        TextView priceView = findViewById(R.id.editTextPrice);
        String registeredPrice = priceView.getText().toString();
        TextView descriptionView = findViewById(R.id.editTextDescription);
        String registeredDescription = descriptionView.getText().toString();

        BigDecimal decimalPrice = new BigDecimal(-1);
        boolean invalidDecimal = false;
        try {
            decimalPrice = new BigDecimal(registeredPrice);
        } catch (NumberFormatException e) {
            invalidDecimal = true;
        }

        if (registeredName.trim().isEmpty() ||
                invalidDecimal ||
                registeredDescription.trim().isEmpty()){
            Toast.makeText(AddFood.this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            CrudFood crud = new CrudFood();
            Food food = new Food(registeredName, decimalPrice, registeredDescription, type);
            crud.insertFood(food, id);
            Intent intent = new Intent(this, RestaurantActivity.class);
            startActivity(intent);
        }
    }
}