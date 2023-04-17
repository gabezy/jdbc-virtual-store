package com.jdbc.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private Integer id;
    private String name;
    private List<Product> products = new ArrayList<>();


    public Category (String name) {
        this.name = name;
    }

    public Category (Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add (Product product) {
        this.products.add(product);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
