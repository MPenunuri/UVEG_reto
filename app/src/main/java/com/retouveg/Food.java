package com.retouveg;

import java.math.BigDecimal;

public class Food {
    public long id;
    public String name;
    public BigDecimal price;
    public String description;
    public String type;
    public Food (long id, String name, BigDecimal price, String description, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

    public Food(String name, BigDecimal price, String description, String type) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.type = type;
    }

}
