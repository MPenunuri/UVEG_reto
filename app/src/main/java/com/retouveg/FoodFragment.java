package com.retouveg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class FoodFragment extends Fragment implements RecyclerViewProductAdapter.OnItemClickListener {
    protected ArrayList<Food> products = new ArrayList<>();
    protected ArrayList<Food> filteredProducts = new ArrayList<>();
    protected RecyclerViewProductAdapter adapter;
    protected RestaurantViewModel viewModel;
    protected long restaurantId;
    public String type;
    public int productId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        type = getType();
    }

    @Override
    public void onResume() {
        super.onResume();
        Activity activity = getActivity();
        if (activity != null) {
            viewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(RestaurantViewModel.class);
            if (viewModel.getData() != null) {
                viewModel.getData().observe(this, restaurant -> {
                    if (restaurant != null) {
                        restaurantId = restaurant.id;
                        loadProducts();
                    }
                });
            }
        }
    }

    public void loadProducts() {
        products.clear();
        CrudFood crudFood = new CrudFood();
        String selection = RestaurantContract.FoodEntry.COLUMN_RESTAURANT_ID + " = ? AND " +
                RestaurantContract.FoodEntry.COLUMN_TYPE + " = ?";
        String[] selectionArgs = { String.valueOf(restaurantId), type };
        try (Cursor cursor = crudFood.queryFood(selection, selectionArgs)){
            while (cursor.moveToNext()) {
                int idIndex = cursor.getColumnIndexOrThrow("_id");
                int nameIndex = cursor.getColumnIndexOrThrow(RestaurantContract.FoodEntry.COLUMN_NAME);
                int priceIndex = cursor.getColumnIndexOrThrow(RestaurantContract.FoodEntry.COLUMN_PRICE);
                int descriptionIndex = cursor.getColumnIndexOrThrow(RestaurantContract.FoodEntry.COLUMN_DESCRIPTION);
                int typeIndex = cursor.getColumnIndexOrThrow(RestaurantContract.FoodEntry.COLUMN_TYPE);
                if (idIndex != -1 && nameIndex != -1 && priceIndex != -1 &&
                        descriptionIndex != -1 && typeIndex != -1) {
                    productId = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    double price = cursor.getDouble(priceIndex);
                    String description = cursor.getString(descriptionIndex);
                    String type = cursor.getString(typeIndex);
                    products.add(new Food(productId, name, new BigDecimal(price), description, type));
                }
            }
        } finally {
            App.getDBHelper().close();
        }
        filteredProducts = new ArrayList<>(products);
        filter("");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResourceId(), container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewProduct);
        adapter = new RecyclerViewProductAdapter(filteredProducts);
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this::setContextMenuAndListeners);
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);

        Context context = getContext();
        if (context != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        return view;
    }

    private void setContextMenuAndListeners(View view, Food food){
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.context_menu_product, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.editar) {
                Intent intent = new Intent(getContext(), EditRestaurant.class);
                intent.putExtra("RESTAURANT_ID", restaurantId);
                intent.putExtra("PRODUCT_ID", productId);
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });

        popupMenu.show();
    }

    protected abstract String getType();

    protected abstract int getLayoutResourceId();
    public void onItemClick(int position) {
        Food food = filteredProducts.get(position);
        Intent intent = new Intent(getContext(), ProductActivity.class);
        intent.putExtra("PRODUCT_NAME", food.name);
        intent.putExtra("PRODUCT_PRICE", food.price.doubleValue());
        startActivity(intent);
    }


    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.optionSearch);

        if (searchItem != null && searchItem.getActionView() instanceof androidx.appcompat.widget.SearchView) {
            androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();
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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.optionAdd) {
            Intent intent = new Intent(getContext(), AddFood.class);
            intent.putExtra("RESTAURANT_ID", restaurantId);
            intent.putExtra("TYPE", type);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void filter(String text) {
        filteredProducts.clear();

        for (Food item : products) {
            if (item.name.toLowerCase().contains(text.toLowerCase())) {
                filteredProducts.add(item);
            }
        }

        adapter.filterList(filteredProducts);
    }
}
