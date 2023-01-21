package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

public class AuthorManager {
    public List<Author> getAll() throws BookstoreException {
        return DaoFactory.authorDao().getAll();
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.authorDao().delete(id);
    }
}
