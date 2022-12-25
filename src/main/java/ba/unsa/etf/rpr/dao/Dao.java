package ba.unsa.etf.rpr.dao;

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
    T getById(int id);

    /**
     * Adds an entity to database
     * @param item item to add
     */
    void save(T item);

    /**
     * Updates an entity in database based on it's ID
     * @param id primary key of an entity
     * @return updated entity from database
     */
    T update(int id);

    /**
     * Deletes and entity from database base on it's ID
     * @param id primary key of an entity
     */
    void delete(int id);

    /**
     * Returns all entities from database
     * @return List of all entities from database
     */
    List<T> getAll();
}
