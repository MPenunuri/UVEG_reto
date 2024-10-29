package com.retouveg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Objects;

public class EditRestaurant extends AppCompatActivity {
    TextView textView;
    private double id;
    CrudRestaurant crud;
    Intent intent;
    private final String selection = "_id = ?";
    private String[] selectionArgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        id = getIntent().getLongExtra("RESTAURANT_ID",-1);
        String name = getIntent().getStringExtra("RESTAURANT_NAME");

        textView = findViewById(R.id.editTextName);
        textView.setText(name);
        selectionArgs = new String[]{String.valueOf(id)};

        crud = new CrudRestaurant();
        intent = new Intent(this, MainActivity.class);

        Button btnEdit = findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> updateRestaurant());
        Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(v -> deleteRestaurant());
    }
    private void updateRestaurant() {
        String registeredName = textView.getText().toString();
        if (registeredName.trim().isEmpty()){
            Toast.makeText(EditRestaurant.this, "Error", Toast.LENGTH_SHORT).show();
        } else {
            crud.updateRestaurant(registeredName, selection, selectionArgs);
            startActivity(intent);
        }
    }
    private void deleteRestaurant() {
        crud.deleteRestaurant(selection,selectionArgs);
        startActivity(intent);
    }
}