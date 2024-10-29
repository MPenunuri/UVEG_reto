package com.retouveg;

public class Comida extends FoodFragment {
    @Override
    public String getType() {
        return "food";
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_comida;
    }
}