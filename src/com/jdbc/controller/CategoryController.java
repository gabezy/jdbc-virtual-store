package com.jdbc.controller;

import com.jdbc.dao.CategoryDAO;
import com.jdbc.model.Category;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryController {

    private CategoryDAO categoryRepository;

    public CategoryController (CategoryDAO dao) {
        this.categoryRepository = dao;
    }

    public List<Category> list () {
        List<Category> categories = new ArrayList<>();
        for (Category category : categoryRepository.get()) {
            categories.add(category);
        }
        return categories;
    }
}
