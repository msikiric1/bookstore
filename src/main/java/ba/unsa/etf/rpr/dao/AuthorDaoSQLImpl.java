package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Muaz Sikiric
 */
public class AuthorDaoSQLImpl extends AbstractDao<Author> implements AuthorDao {
    /**
     * Constructor used for connecting to the database
     */
    public AuthorDaoSQLImpl() {
        super("authors");
    }

    @Override
    public Author rowToObject(ResultSet rs) throws BookstoreException {
        Author author = new Author();
        try {
            author.setId(rs.getInt("id"));
            author.setName(rs.getString("name"));
            author.setAddress(rs.getString("address"));
            author.setPhone(rs.getString("phone"));
            return author;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> objectToRow(Author object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("name", object.getName());
        row.put("address", object.getAddress());
        row.put("phone", object.getPhone());
        return row;
    }

    @Override
    public List<Author> searchByName(String name) {
        String query = "SELECT * FROM authors WHERE name = ?";
        List<Author> authors = new ArrayList<>();
        String preparedName = name.trim().substring(0, 1).toUpperCase() + name.trim().substring(1).toLowerCase();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, preparedName);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setName(rs.getString("name"));
                author.setAddress(rs.getString("address"));
                author.setPhone(rs.getString("phone"));
                authors.add(author);
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }
}
