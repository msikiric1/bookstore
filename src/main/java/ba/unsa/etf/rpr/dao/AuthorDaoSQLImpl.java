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
    public List<Author> searchByName(String name) throws BookstoreException {
        String query = "SELECT * FROM authors WHERE name LIKE concat('%', ?, '%')";
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, name.trim());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                authors.add(rowToObject(rs));
            }
            rs.close();
            return authors;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }
}
