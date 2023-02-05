package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

/**
 * Root interface for all DAO classes
 * @author Muaz Sikiric
 */
public interface Dao<T> {
    /**
     * Returns entity from the database based on it's ID
     * @param id primary key of an entity
     * @return entity
     */
    T getById(int id) throws BookstoreException;

    /**
     * Adds an entity to the database
     * @param item item to add
     */
    T add(T item) throws BookstoreException;

    /**
     * Updates an entity in the database based on it's ID
     * @param item primary key of an entity
     * @return updated entity
     */
    T update(T item) throws BookstoreException;

    /**
     * Deletes an entity from the database base on it's ID
     * @param id primary key of an entity
     */
    void delete(int id) throws BookstoreException;

    /**
     * Returns all entities from the database
     * @return list of all entities
     */
    List<T> getAll() throws BookstoreException;
}
