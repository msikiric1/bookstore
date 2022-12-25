package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class BookDaoSQLImpl implements BookDao {
    private Connection conn;

    public BookDaoSQLImpl() {
        Properties prop = new Properties();
        try {
            this.conn = DriverManager.getConnection("jdbc:mysql://" + prop.getProperty("db.host") + "/" + prop.getProperty("db.scheme") + "/" + prop.getProperty("db.port"),
                    prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Book getById(int id) {
        return null;
    }

    @Override
    public void save(Book item) {

    }

    @Override
    public Book update(int id) {
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
