package com.retouveg;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
public class RestaurantViewModel extends AndroidViewModel {
    private MutableLiveData<Restaurant> data = new MutableLiveData<>();

    public RestaurantViewModel(Application application) {
        super(application);
    }

    public void setData(Restaurant newData) {
        data.setValue(newData);
    }

    public LiveData<Restaurant> getData() {
        return data;
    }
}