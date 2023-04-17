package com.jdbc.dao;
import com.jdbc.model.Category;
import java.sql.SQLException;
import java.util.List;

public interface CategoryDAO {
    public void create (Category category);
    public List<Category> get ();
    public Category findUnique (Integer id);
    public List<Category> findMany (String search);
}
