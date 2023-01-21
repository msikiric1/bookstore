package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

public class CategoryManager {
    public List<Category> getAll() throws BookstoreException {
        return DaoFactory.categoryDao().getAll();
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.categoryDao().delete(id);
    }
}
