package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Abstract class that contains all base sql operations (create, read, update and delete)
 * @param <T> parametrized type
 * @author Muaz Sikiric
 */
public abstract class AbstractDao<T> implements Dao<T> {
    private Connection conn;
    private String table;

    /**
     * Constructor used for connecting to the database
     * @param table Table name
     */
    public AbstractDao(String table) {
        this.table = table;
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("config.properties"));
            conn = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch(IOException | SQLException e) {
            System.out.println("Greska prilikom povezivanja na bazu podataka:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T getById(int id) throws BookstoreException {
        String query = "SELECT * FROM ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, table);
            stmt.setInt(2, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                T item = rowToObject(rs);
                rs.close();
                return item;
            }
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
        throw new BookstoreException("Object not found.");
    }

    @Override
    public void save(T item) throws BookstoreException {
        Map<String, T> row = objectToRow(item);

        StringBuilder insert = new StringBuilder()
                .append("INSERT INTO ? (")
                .append()
                .append(") VALUES (")
                .append()
                .append(")");

        try {
            PreparedStatement stmt = conn.prepareStatement(insert.toString());
            // TODO:

        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
        throw new BookstoreException();
    }

    @Override
    public T update(T item) throws BookstoreException {

    }

    @Override
    public void delete(int id) throws BookstoreException {
        String delete = "DELETE FROM ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(delete);
            stmt.setString(1, table);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
        throw new BookstoreException("Object doesn't exist.")
    }

    @Override
    public List<T> getAll() throws BookstoreException {

    }

    /**
     * Converts rows to objects
     * @param rs result of a query (row/s)
     * @return object
     * @throws BookstoreException
     */
    public abstract T rowToObject(ResultSet rs) throws BookstoreException;

    /**
     * Converts objects to rows
     * @param object object that needs to be transformed into row
     * @return map that has string and objects as key-value pairs
     * @throws BookstoreException
     */
    public abstract Map<String, T> objectToRow(T object) throws BookstoreException;
}
