package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.sql.*;
import java.util.List;
import java.util.Properties;

public class AuthorDaoSQLImpl implements AuthorDao {
    private Connection conn;

    /**
     * Constructor used for connecting to the database
     */
    public AuthorDaoSQLImpl() {
        Properties prop = new Properties();
        try {
            this.conn = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    }

    @Override
    public Author update(Author item) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Author> getAll() {
        return null;
    }

    @Override
    public List<Author> searchByName(String name) {
        return null;
    }
}
