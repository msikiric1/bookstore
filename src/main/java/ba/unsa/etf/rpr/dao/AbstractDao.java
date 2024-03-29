package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Identifiable;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * Abstract class that contains all base sql operations (create, read, update and delete)
 * @param <T> parametrized type
 * @author Muaz Sikiric
 */
public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {
    private static Connection connection = null;
    private String table;

    /**
     * Constructor used for connecting to the database
     * @param table Table name
     */
    public AbstractDao(String table) {
        this.table = table;
        Properties prop = new Properties();
        try {
            prop.load(ClassLoader.getSystemResource("config.properties").openStream());
            String url = prop.getProperty("db.url");
            String username = prop.getProperty("db.username");
            String password = prop.getProperty("db.password");
            AbstractDao.connection = DriverManager.getConnection(url, username, password);
        } catch (IOException | SQLException e) {
            System.out.println("Greska prilikom povezivanja na bazu podataka:");
            System.out.println(e.getMessage());
            System.exit(0);
        } finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }));
        }
    }

    public static Connection getConnection() {
        return AbstractDao.connection;
    }

    public String getTable() {
        return table;
    }

    @Override
    public T getById(int id) throws BookstoreException {
        String query = "SELECT * FROM " + getTable() + " WHERE id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setInt(1, id);
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
    public T add(T item) throws BookstoreException {
        Map<String, Object> row = objectToRow(item);
        Map.Entry<String, String> insertStrings = toPreparedInsertParts(row);
        StringBuilder insert = new StringBuilder()
                .append("INSERT INTO ").append(getTable()).append(" (")
                .append(insertStrings.getKey()).append(") VALUES (")
                .append(insertStrings.getValue()).append(")");

        try {
            PreparedStatement stmt = getConnection().prepareStatement(insert.toString(), Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for(Map.Entry<String, Object> entry : row.entrySet()) {
                if(entry.getKey().equals("id")) continue;
                stmt.setObject(i, entry.getValue());
                i++;
            }
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1)); // 1st column contains generated key (id) for the inserted id
            return item;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public T update(T item) throws BookstoreException {
        Map<String, Object> row = objectToRow(item);
        String updateString = toPreparedUpdateParts(row);
        StringBuilder update = new StringBuilder()
                .append("UPDATE ").append(getTable()).append(" SET ")
                .append(updateString).append(" WHERE id = ?");

        try {
            PreparedStatement stmt = getConnection().prepareStatement(update.toString());
            int i = 1;
            for(Map.Entry<String, Object> entry : row.entrySet()) {
                if(entry.getKey().equals("id")) continue;
                stmt.setObject(i, entry.getValue());
                i++;
            }
            stmt.setObject(i, item.getId());
            stmt.executeUpdate();
            return item;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(int id) throws BookstoreException {
        String delete = "DELETE FROM " + getTable() + " WHERE id = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(delete);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public List<T> getAll() throws BookstoreException {
        String query = "SELECT * FROM " + getTable();
        List<T> items = new ArrayList<>();
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                items.add(rowToObject(rs));
            }
            rs.close();
            return items;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    /**
     * Converts a row to an object
     * @param rs result of a query (row)
     * @return object
     * @throws BookstoreException
     */
    public abstract T rowToObject(ResultSet rs) throws BookstoreException;

    /**
     * Converts an object to a row
     * @param object object that needs to be transformed into row
     * @return map that has string and objects as key-value pairs (represents a row)
     */
    public abstract Map<String, Object> objectToRow(T object);

    /**
     * Utility method that returns map entry with two strings as key-value pair that
     * represent columns (e.g. "(id,name,address)") and question marks (e.g. "?,?,?").
     * Strings are prepared for SQL insertion
     * @param row Map that represents a row
     * @return Map<String, String>
     */
    private Map.Entry<String, String> toPreparedInsertParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int i = 0;
        for(Map.Entry<String, Object> entry : row.entrySet()) {
            i++;
            if(entry.getKey().equals("id")) continue;
            columns.append(entry.getKey());
            questions.append("?");
            if(i != row.size()) {
                columns.append(",");
                questions.append(",");
            }
        }
        return new AbstractMap.SimpleEntry<String, String>(columns.toString(), questions.toString());
    }

    /**
     *  Utility method that returns a prepared string for SQL update operation
     *  (e.g. "id = ?,name = ?,address = ?")
     *  @param row Map that represents a row
     *  @return Prepared string
     */
    private String toPreparedUpdateParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();

        int i = 0;
        for(Map.Entry<String, Object> entry : row.entrySet()) {
            i++;
            if(entry.getKey().equals("id")) continue;
            columns.append(entry.getKey()).append(" = ?");
            if(i != row.size())
                columns.append(",");
        }
        return columns.toString();
    }
}
