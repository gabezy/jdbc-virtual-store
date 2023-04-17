package com.jdbc.dao;
import com.jdbc.model.Category;
import com.jdbc.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySQLCategoryDAO implements CategoryDAO {
    private Connection connection;

    public MySQLCategoryDAO(Connection connection){
        try {
            this.connection = connection;
            this.connection.setAutoCommit(false);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void create(Category category) {

    }

    @Override
    public List<Category> get() {
        try {
            List<Category> categoryList = new ArrayList<>();
            try (PreparedStatement stm = this.connection.prepareStatement("SELECT * FROM category")) {

                stm.execute();
                try (ResultSet resultSet = stm.getResultSet()) {
                    while(resultSet.next()) {
                        Integer id = resultSet.getInt("id");
                        String name = resultSet.getString("name");
                        categoryList.add(new Category(id, name));
                    }
                }
            }
            return categoryList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    public List<Category> getWithProduct ()  {
        try {
            Category last = null;
            List<Category> categoryList = new ArrayList<>();
            String sql = "SELECT C.id, C.name, P.id, P.name , P.description FROM product P "
                    + "JOIN category C ON P.category_id = C.id";
            try (PreparedStatement stm = this.connection.prepareStatement(sql)) {
                stm.execute();

                try (ResultSet resultSet = stm.getResultSet()) {
                    while (resultSet.next()) {
                        Integer categoryId = resultSet.getInt(1);
                        String categoryName = resultSet.getString(2);
                        if (last == null || !last.getName().equals(categoryName)) {
                            Category category = new Category(categoryId, categoryName);
                            categoryList.add(category);
                            last = category;
                        }
                        // Instancing the product
                        Integer productId = resultSet.getInt(3);
                        String productName = resultSet.getString(4);
                        String productDescription = resultSet.getString(5);
                        Product product = new Product(productId, productName, productDescription);

                        // Add product to the current category
                        last.add(product);
                    }
                }
            }
            return categoryList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Category findUnique(Integer id) {
        return null;
    }

    @Override
    public List<Category> findMany(String search)  {
        return null;
    }
}
