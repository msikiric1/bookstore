package ba.unsa.etf.rpr.dao;

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
}
