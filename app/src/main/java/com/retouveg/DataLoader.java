package com.retouveg;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.util.ArrayList;

public class DataLoader {
    public static void loadData(@NonNull Context context) {
        String[] resNames = new String[]{"Restaurante A", "Restaurante B", "Restaurante C"};
        ArrayList<Food> food = new ArrayList<>();
        String lorem = context.getResources().getString(R.string.lorem);
        food.add(new Food("Hamburguesa", new BigDecimal("98.82"), lorem, "food"));
        food.add(new Food("Pizza", new BigDecimal("89.28"), lorem, "food"));
        food.add(new Food("Sopa", new BigDecimal("62.73"), lorem, "food"));
        food.add(new Food("Coca-cola", new BigDecimal("25.75"), lorem, "drink"));
        food.add(new Food("Agua", new BigDecimal("15.50"), lorem, "drink"));
        food.add(new Food("Cerveza", new BigDecimal("40.50"), lorem, "drink"));
        food.add(new Food("Papas fritas", new BigDecimal("28.50"), lorem, "complement"));
        food.add(new Food("Verduras", new BigDecimal("45.75"), lorem, "complement"));
        food.add(new Food("Pay de queso", new BigDecimal("30.45"), lorem, "complement"));
        for (String resName : resNames) {
            CrudRestaurant crudRestaurant = new CrudRestaurant();
            long restaurantId = crudRestaurant.insertRestaurant(resName);
            for (Food f : food) {
                if(restaurantId != -1) {
                    CrudFood crudFood = new CrudFood();
                    crudFood.insertFood(f, restaurantId);
                }
            }
        }
    }
}