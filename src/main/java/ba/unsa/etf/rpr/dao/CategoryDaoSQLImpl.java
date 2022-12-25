package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Muaz Sikiric
 */
public class CategoryDaoSQLImpl extends AbstractDao<Category> implements CategoryDao {

    /**
     * Constructor used for connecting to the database
     */
    public CategoryDaoSQLImpl() {
        super("categories");
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
    public Map<String, Object> objectToRow(Category object) {
        Map<String, Object> row = new TreeMap<>(); // Tree map automatically sorts its elements
        row.put("id", object.getId());
        row.put("name", object.getName());
        return row;
    }
}
