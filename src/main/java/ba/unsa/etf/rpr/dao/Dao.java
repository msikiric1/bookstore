package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

/**
 * Root interface for all DAO classes
 * @author Muaz Sikiric
 */
public interface Dao<T> {
    /**
     * Returns entity from database based on it's ID
     * @param id primary key of an entity
     * @return entity from database
     */
    T getById(int id) throws BookstoreException;

    /**
     * Adds an entity to database
     * @param item item to add
     */
    void save(T item) throws BookstoreException;

    /**
     * Updates an entity in database based on it's ID
     * @param item primary key of an entity
     * @return updated entity from database
     */
    T update(T item) throws BookstoreException;

    /**
     * Deletes and entity from database base on it's ID
     * @param id primary key of an entity
     */
    void delete(int id) throws BookstoreException;

    /**
     * Returns all entities from database
     * @return List of all entities from database
     */
    List<T> getAll() throws BookstoreException;
}
