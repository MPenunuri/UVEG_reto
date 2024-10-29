package com.retouveg;

public class Bebidas extends FoodFragment {
    @Override
    public String getType() {
        return "drink";
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_bebidas;
    }
}