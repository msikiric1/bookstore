package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.*;

public class CategoryDaoSQLImpl extends AbstractDao<Category> implements CategoryDao {

    /**
     * Constructor used for connecting to the database
     */
    public CategoryDaoSQLImpl() {
        super("categories");
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
        String update = "UPDATE categories SET name = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, item.getName());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
            return item;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM categories WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getAll() {
        String query = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                categories.add(category);
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category rowToObject(ResultSet rs) throws BookstoreException {
        Category category = new Category();
        try {
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            return category;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> objectToRow(Category object) throws BookstoreException {
        Map<String, Object> row =  new TreeMap<>(); // Tree map automatically sorts its elements
        row.put("id", object.getId());
        row.put("name", object.getName());
        return row;
    }
}
