package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
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
            System.out.println("Greska prilikom povezivanja na bazu podataka:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Book getById(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                Book book = new Book();
                book.setId(id);
                book.setTitle(rs.getString("title"));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("author_id")));
                book.setPublished(rs.getDate("published"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(new CategoryDaoSQLImpl().getById(rs.getInt("category_id")));
                rs.close();
                return book;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book save(Book item) {
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
        String update = "UPDATE books SET title = ?, author_id = ?, published = ?, price = ?, category_id = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, item.getTitle());
            stmt.setInt(2, item.getAuthor().getId());
            stmt.setDate(3, item.getPublished());
            stmt.setDouble(4, item.getPrice());
            stmt.setInt(5, item.getCategory().getId());
            stmt.setInt(6, item.getId());
            stmt.executeUpdate();
            return item;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(int id) {
        String delete = "DELETE * FROM books WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> getAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("author_id")));
                book.setPublished(rs.getDate("published"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(new CategoryDaoSQLImpl().getById(rs.getInt("category_id")));
                books.add(book);
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> getBetweenPublishedDates(Date lowerBound, Date upperBound) {
        String query = "SELECT * FROM books WHERE published BETWEEN ? AND ?";
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setDate(1, lowerBound);
            stmt.setDate(2, upperBound);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("author_id")));
                book.setPublished(rs.getDate("published"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(new CategoryDaoSQLImpl().getById(rs.getInt("category_id")));
                books.add(book);
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    @Override
    public List<Book> searchByCategory(Category category) {
        String query = "SELECT * FROM books WHERE category_id = ?";
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, category.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(new AuthorDaoSQLImpl().getById(rs.getInt("author_id")));
                book.setPublished(rs.getDate("published"));
                book.setPrice(rs.getDouble("price"));
                book.setCategory(category);
                books.add(book);
            }
            rs.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
}
