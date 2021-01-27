package com.jandro.market.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int productId;
    private String nombre;
    private int categoryId;
    private double price;
    private int stock;
    private boolean active;
    private Category category;
}
