package com.jdbc.dao;
import com.jdbc.model.Category;
import com.jdbc.model.Product;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLProductDAO implements ProductDAO {
    private Connection connection;

    public MySQLProductDAO(Connection connection) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
    }
    @Override
    public void create (Product product) {
        try {
            String sql = "INSERT INTO product (name, description, category_id) VALUES (?, ?, ?)";
            try (PreparedStatement stm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
                stm.setString(1, product.getName().toLowerCase());
                stm.setString(2, product.getDescription().toLowerCase());
                stm.setInt(3, product.getCategoryId());
                stm.execute();
                ResultSet resultSet = stm.getGeneratedKeys();
                while (resultSet.next()) {
                    product.setId(resultSet.getInt(1));
                }
                this.connection.commit();
            } catch (SQLException ex) {
                this.connection.rollback();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }



    public List<Product> get () {
        try {
            List<Product> productList = new ArrayList<Product>();
            try (PreparedStatement stm = this.connection.prepareStatement("SELECT * FROM product")) {
                stm.execute();
                transformResultSetInProduct(productList, stm);
            }
            return productList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

    }

    @Override
    public Product findUnique(Integer id) {
        try {
            String sql = "SELECT * FROM product WHERE id = ?";
            PreparedStatement stm = this.connection.prepareStatement(sql);
            stm.setInt(1, id);
            stm.execute();
            ResultSet resultSet = stm.getResultSet();
            Product product = null;
            while (resultSet.next()) {
                Integer productId = resultSet.getInt("id");
                if (productId != id) {
                    return null;
                }
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Integer categoryId = resultSet.getInt("category_id");
                product = new Product(id, name, description);
            }
            return product;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Product> findByCategory(Category category) {
        try {
            List<Product> productList = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE category_id = ?";
            try (PreparedStatement stm = this.connection.prepareStatement(sql)) {
                stm.setInt(1, category.getId());
                stm.execute();
                transformResultSetInProduct(productList, stm);
            }
            return productList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Product> findMany(String search) {
        try {
            List<Product> productList = new ArrayList<>();
            String sql = "SELECT * FROM product WHERE name LIKE ? OR description LIKE ?";
            try (PreparedStatement stm = this.connection.prepareStatement(sql)) {
                String query = "%" + search + "";
                stm.setString(1, query.toLowerCase());
                stm.setString(2, query.toLowerCase());
                stm.execute();
                transformResultSetInProduct(productList, stm);
            }
            return productList;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void update(String name, String description, Integer id) {
        try {
            try {
                String sql = "UPDATE product SET name = ?, description = ? WHERE id = ?";
                PreparedStatement stm = this.connection.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, description);
                stm.setInt(3, id);
                stm.execute();
                this.connection.commit();
            } catch (SQLException ex) {
                this.connection.rollback();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void delete(Integer id)  {
        try {
            try {
                String sql = "DELETE FROM product WHERE id = ?";
                PreparedStatement stm = this.connection.prepareStatement(sql);
                stm.setInt(1, id);
                stm.execute();
                this.connection.commit();
            } catch (SQLException ex) {
                this.connection.rollback();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void transformResultSetInProduct (List<Product> products, PreparedStatement stm)  {
        try {
            try (ResultSet resultSet = stm.getResultSet()) {
                while (resultSet.next()) {
                    Integer id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Product product = new Product(id, name, description);
                    products.add(product);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
