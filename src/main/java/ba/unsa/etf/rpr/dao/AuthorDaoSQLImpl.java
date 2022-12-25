package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AuthorDaoSQLImpl extends AbstractDao<Author> implements AuthorDao {
    /**
     * Constructor used for connecting to the database
     */
    public AuthorDaoSQLImpl() {
        super("authors");
    }

    @Override
    public Author getById(int id) {
        String query = "SELECT * FROM authors WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Author author = new Author();
                author.setId(id);
                author.setName(rs.getString("name"));
                author.setAddress(rs.getString("address"));
                author.setPhone(rs.getString("phone"));
                rs.close();
                return author;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Author item) {
        String insert = "INSERT INTO authors (name, address, phone) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getAddress());
            stmt.setString(3, item.getPhone());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Author update(Author item) {
        String update = "UPDATE authors SET name = ?, address = ?, phone = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, item.getName());
            stmt.setString(2, item.getAddress());
            stmt.setString(3, item.getPhone());
            stmt.setInt(4, item.getId());
            stmt.executeUpdate();
            return item;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE FROM authors WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Author> getAll() {
        String query = "SELECT * FROM authors";
        List<Author> authors = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
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
    public Map<String, Object> objectToRow(Author object) throws BookstoreException {
        return null;
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
