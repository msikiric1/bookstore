package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Category;

import java.sql.*;
import java.util.List;
import java.util.Properties;
import java.util.PropertyResourceBundle;

public class CategoryDaoSQLImpl implements CategoryDao {
    private Connection conn;

    /**
     * Constructor used for connecting to the database
     */
    public CategoryDaoSQLImpl() {
        Properties prop = new Properties();
        try {
            this.conn = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getById(int id) {
        return null;
    }

    @Override
    public void save(Category item) {

    }

    @Override
    public Category update(Category item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Category> getAll() {
        return null;
    }
}
