package com.retouveg;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class RestaurantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        RestaurantViewModel viewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        long id = getIntent().getLongExtra("RESTAURANT_ID", -1);
        String name = getIntent().getStringExtra("RESTAURANT_NAME");
        if (name != null && id != -1) {
            viewModel.setData(new Restaurant(id, name));
        }
        toolbar.setTitle(Objects.requireNonNull(viewModel.getData().getValue()).name);


        ViewPager2 viewPager = findViewById(R.id.viewPager2);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Lifecycle lifecycle = getLifecycle();
        viewPager.setAdapter(new ViewPagerAdapter(fragmentManager, lifecycle));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Comida");
                    break;
                case 1:
                    tab.setText("Bebidas");
                    break;
                case 2:
                    tab.setText("Complementos");
                    break;
            }
        }).attach();

        String option = getIntent().getStringExtra("OPTION");

        switch (Objects.requireNonNull(option)) {
            case "Bebidas":
                viewPager.setCurrentItem(1);
                break;
            case "Complementos":
                viewPager.setCurrentItem(2);
                break;
            default:
                viewPager.setCurrentItem(0);
                break;
        }

    }
}