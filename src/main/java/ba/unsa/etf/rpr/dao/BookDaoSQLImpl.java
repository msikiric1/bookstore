package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that implements methods from BookDao interface
 * @author Muaz Sikiric
 */
public class BookDaoSQLImpl extends AbstractDao<Book> implements BookDao {
    private static BookDaoSQLImpl instance = null;
    /**
     * Constructor that is used for connection to the database
     */
    private BookDaoSQLImpl() {
        super("books");
    }

    public static BookDaoSQLImpl getInstance() {
        if(instance == null)
            instance = new BookDaoSQLImpl();
        return instance;
    }

    public static void removeInstance() {
        if(instance != null)
            instance = null;
    }

    @Override
    public Book rowToObject(ResultSet rs) throws BookstoreException {
        Book book = new Book();
        try {
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(DaoFactory.authorDao().getById(rs.getInt("author_id")));
            book.setPublished(rs.getDate("published").toLocalDate());
            book.setPrice(rs.getDouble("price"));
            book.setCategory(DaoFactory.categoryDao().getById(rs.getInt("category_id")));
            return book;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> objectToRow(Book object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("title", object.getTitle());
        row.put("author_id", object.getAuthor().getId());
        row.put("published", object.getPublished());
        row.put("price", object.getPrice());
        row.put("category_id", object.getCategory().getId());
        return row;
    }

    @Override
    public List<Book> getBetweenPublishedDates(Date lowerBound, Date upperBound) throws BookstoreException {
        String query = "SELECT * FROM books WHERE published BETWEEN ? AND ?";
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setDate(1, lowerBound);
            stmt.setDate(2, upperBound);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                books.add(rowToObject(rs));
            }
            rs.close();
            return books;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public List<Book> searchByCategory(Category category) throws BookstoreException {
        String query = "SELECT * FROM books WHERE category_id = ?";
        List<Book> books = new ArrayList<>();
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, category.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                books.add(rowToObject(rs));
            }
            rs.close();
            return books;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }
}
