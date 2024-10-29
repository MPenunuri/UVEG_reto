package com.retouveg;
public class Complementos extends FoodFragment {
    @Override
    public String getType() {
        return "complement";
    }
    @Override
    protected int getLayoutResourceId() {
        return R.layout.fragment_complementos;
    }
}