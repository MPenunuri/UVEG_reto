package com.retouveg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class AddRestaurant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(v -> createRestaurant());
    }
    private void createRestaurant() {
        TextView textView = findViewById(R.id.editTextName);
        String registeredName = textView.getText().toString();
        if (registeredName.trim().isEmpty()){
            Toast.makeText(AddRestaurant.this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            CrudRestaurant crud = new CrudRestaurant();
            crud.insertRestaurant(registeredName);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}