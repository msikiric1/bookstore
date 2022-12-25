package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorDaoSQLImpl implements AuthorDao {
    private Connection conn;

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
