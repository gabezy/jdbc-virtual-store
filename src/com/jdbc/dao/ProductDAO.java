package com.jdbc.dao;

import com.jdbc.model.Category;
import com.jdbc.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    public void create (Product product);
    public List<Product> get ();
    public Product findUnique (Integer id);
    public List<Product> findMany (String search);

    public List<Product> findByCategory (Category category);

    public void update (String name, String description, Integer id);

    public void delete (Integer id);
}
