package com.jdbc.factory;

import com.jdbc.controller.ProductController;
import com.jdbc.dao.MySQLProductDAO;
import com.jdbc.dao.ProductDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductControllerFactory {

    private Connection connection;
    private ProductDAO dao;
    private ProductController controller;

    public ProductControllerFactory () throws SQLException {
        this.connection = new ConnectionFactory().connection();
        this.dao = new MySQLProductDAO(connection);
        this.controller = new ProductController(dao);
    }

    public ProductController getController() {
        return controller;
    }
}
