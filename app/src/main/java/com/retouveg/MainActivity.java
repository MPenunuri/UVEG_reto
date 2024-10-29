package com.retouveg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements RecyclerViewRestaurantAdapter.OnItemClickListener {
    private RecyclerViewRestaurantAdapter adapter;
    private final ArrayList<Restaurant> restaurantNames = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CrudRestaurant crudRestaurant = new CrudRestaurant();
        try (Cursor cursor = crudRestaurant.queryAll()) {
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndexOrThrow("_id");
                int nameIndex = cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantEntry.COLUMN_NAME);
                if (idIndex != -1 && nameIndex != -1) {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    restaurantNames.add(new Restaurant(id, name));
                }
            }
        } finally {
            App.getDBHelper().close();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerViewRestaurant);
        adapter = new RecyclerViewRestaurantAdapter(restaurantNames);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this::setContextMenuAndListeners);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        registerForContextMenu(recyclerView);
    }
    public void makeIntent(Restaurant restaurant, String option) {
        Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
        intent.putExtra("RESTAURANT_ID", restaurant.id);
        intent.putExtra("RESTAURANT_NAME", restaurant.name);
        intent.putExtra("OPTION", option);
        startActivity(intent);
    }
    private void setContextMenuAndListeners(View view, Restaurant restaurant){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.action_info) {
                Intent intent = new Intent(MainActivity.this, EditRestaurant.class);
                intent.putExtra("RESTAURANT_ID", restaurant.id);
                intent.putExtra("RESTAURANT_NAME", restaurant.name);
                startActivity(intent);
                return true;
            } else if (menuItem.getItemId() == R.id.action_comida) {
                makeIntent(restaurant, "Comida");
                return  true;
            } else if (menuItem.getItemId() == R.id.action_bebidas) {
                makeIntent(restaurant, "Bebidas");
                return  true;
            } else if (menuItem.getItemId() == R.id.action_complementos) {
                makeIntent(restaurant, "Complementos");
                return  true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    @Override
    public void onItemClick(Restaurant restaurant) {
        makeIntent(restaurant, "Comida");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.optionSearch).getActionView();
        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.optionAdd) {
            Intent intent = new Intent(this, AddRestaurant.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void filter(String text) {
        ArrayList<Restaurant> filteredList = new ArrayList<>();
        for (Restaurant item : restaurantNames) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

}
