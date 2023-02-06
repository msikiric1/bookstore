package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

/**
 * Business logic layer for categories
 * @author Muaz Sikiric
 */
public class CategoryManager {
    /**
     * Adds a category to the database
     * @param category category to add
     * @return added category
     */
    public Category add(Category category) throws BookstoreException {
        validate(category);
        return DaoFactory.categoryDao().add(category);
    }

    /**
     * Returns all categories from the database
     * @return list of all categories
     */
    public List<Category> getAll() throws BookstoreException {
        return DaoFactory.categoryDao().getAll();
    }

    /**
     * Returns a category from the database based on it's ID
     * @param id primary key of a category
     * @return category
     */
    public Category getById(int id) throws BookstoreException {
        return DaoFactory.categoryDao().getById(id);
    }

    /**
     * Updates a category in the database based on it's ID
     * @param category category to update
     * @return updated category
     */
    public Category update(Category category) throws BookstoreException {
        validate(category);
        return DaoFactory.categoryDao().update(category);
    }

    /**
     * Deletes a category from the database base on it's ID
     * @param id primary key of a category
     */
    public void delete(int id) throws BookstoreException {
        DaoFactory.categoryDao().delete(id);
    }

    /**
     * Checks if the category satisfies given constraints
     * @param category category
     * @throws BookstoreException
     */
    public void validate(Category category) throws BookstoreException {
        final int minNameLength = 5;

        if(category.getName().length() < minNameLength)
            throw new BookstoreException("Name needs to be at least " + minNameLength + " characters.");
    }
}
