package ba.unsa.etf.rpr.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
            prop.load(ClassLoader.getSystemResource("config.properties").openStream());
            conn = DriverManager.getConnection(prop.getProperty("db.url"), prop.getProperty("db.username"), prop.getProperty("db.password"));
        } catch(IOException | SQLException e) {
            System.out.println("Greska prilikom povezivanja na bazu podataka:");
            System.out.println(e.getMessage());
        }
    }

    @Override
    public T getById(int id) {

    }

    @Override
    public void save(T item) {

    }

    @Override
    public T update(T item) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<T> getAll() {

    }
}
