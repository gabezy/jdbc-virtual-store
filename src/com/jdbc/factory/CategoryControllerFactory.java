package com.jdbc.factory;


import com.jdbc.controller.CategoryController;
import com.jdbc.dao.MySQLCategoryDAO;
import com.jdbc.dao.CategoryDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class CategoryControllerFactory {

    private Connection connection;
    private CategoryDAO dao;
    private CategoryController controller;

    public CategoryControllerFactory () throws SQLException {
        this.connection = new ConnectionFactory().connection();
        this.dao = new MySQLCategoryDAO(connection);
        this.controller = new CategoryController(this.dao);
    }

    public CategoryController getController() {
        return controller;
    }
}
