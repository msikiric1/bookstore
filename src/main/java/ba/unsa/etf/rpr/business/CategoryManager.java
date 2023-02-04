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
    public Category add(Category category) throws BookstoreException {
        return DaoFactory.categoryDao().add(category);
    }
    public List<Category> getAll() throws BookstoreException {
        return DaoFactory.categoryDao().getAll();
    }

    public Category getById(int id) throws BookstoreException {
        return DaoFactory.categoryDao().getById(id);
    }

    public Category update(Category category) throws BookstoreException {
        return DaoFactory.categoryDao().update(category);
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.categoryDao().delete(id);
    }

    /**
     * Validates the length of the category name
     * @param name category name (min. 5 characters)
     * @throws BookstoreException
     */
    public void validate(String name) throws BookstoreException {
        if(name == null || name.length() < 5)
            throw new BookstoreException("Category name needs to be at least 5 characters.");
    }
}
