package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Muaz Sikiric
 */
public class BookDaoSQLImpl extends AbstractDao<Book> implements BookDao {
    /**
     * Constructor that is used for connection to the database
     */
    public BookDaoSQLImpl() {
        super("books");
    }

    @Override
    public Book rowToObject(ResultSet rs) throws BookstoreException {
        return null;
    }

    @Override
    public Map<String, Object> objectToRow(Book object) throws BookstoreException {
        return null;
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
