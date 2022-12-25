package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BookDaoSQLImpl implements BookDao {
    private Connection conn;

    /**
     * Constructor that is used for connection to the database
     */
    public BookDaoSQLImpl() {
        Properties prop = new Properties();
        try {
            this.conn = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getById(int id) {
        String query = "SELECT * from books WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("author_id")));
                book.setPublished(rs.getDate("published"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(new CategoryDaoSQLImpl().getByID(rs.getDate("category_id")));
                rs.close();
                return book;
            }
            return null;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(Book item) {
        String insert = "INSERT INTO books (title, author_id, published, price, category_id)" +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(insert);
            stmt.setString(1, item.getTitle());
            stmt.setInt(2, item.getAuthor().getId());
            stmt.setDate(3, item.getPublished());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getCategory().getId());
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book update(Book item) {
        String update = "UPDATE books SET title = ?, author_id = ?, published = ?, price = ?, category_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, item.getTitle());
            stmt.setInt(2, item.getAuthor().getId());
            stmt.setDate(3, item.getPublished());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getCategory().getId());
            stmt.executeUpdate();
            return item;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Book> getAll() {
        return null;
    }

    @Override
    public List<Book> getBetweenPublishedDate(Date lowerBound, Date upperBound) {
        return null;
    }
}
