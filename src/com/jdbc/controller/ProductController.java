package com.jdbc.controller;

import com.jdbc.dao.ProductDAO;
import com.jdbc.model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductController {

    private ProductDAO dao;

    public ProductController (ProductDAO dao) {
        this.dao = dao;
    }

    public void delete (Integer id) {
        this.dao.delete(id);
    }

    public void save (Product product) {
        this.dao.create(product);
    }

    public List<Product> list () {
        List<Product> products = new ArrayList<>();
        this.dao.get().forEach(product -> products.add(product));
        return products;

    }

    public void update (String name, String description, Integer id) {
        this.dao.update(name, description, id);
    }
}
