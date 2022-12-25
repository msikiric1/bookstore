package ba.unsa.etf.rpr.dao;

import java.sql.Connection;

/**
 * Abstract class that contains all base sql operations (create, read, update and delete)
 * @param <T> parametrized type
 * @author Muaz Sikiric
 */
public abstract class AbstractDao<T> implements Dao<T> {
    private Connection conn;
    private String table;
}
