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
        String query = "SELECT * FROM categories WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Category category = new Category();
                category.setId(id);
                category.setName(rs.getString("name"));
                rs.close();
                return category;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Category item) {
        String insert = "INSERT INTO categories (name) VALUES (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, item.getName());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
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
