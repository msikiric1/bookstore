package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

/**
 * Business logic layer for authors
 * @author Muaz Sikiric
 */
public class AuthorManager {
    public Author add(Author author) throws BookstoreException {
        return DaoFactory.authorDao().add(author);
    }

    public List<Author> getAll() throws BookstoreException {
        return DaoFactory.authorDao().getAll();
    }

    public Author getById(int id) throws BookstoreException {
        return DaoFactory.authorDao().getById(id);
    }

    public Author update(Author author) throws BookstoreException {
        return DaoFactory.authorDao().update(author);
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.authorDao().delete(id);
    }

    public void validate(String name, String address, String phone) throws BookstoreException {
        if(name == null || name.length() < 3)
            throw new BookstoreException("Author name needs to be at least 3 characters.");
        if(address == null || address.length() < 12)
            throw new BookstoreException("Author address needs to be at least 12 characters.");
        if(phone == null || !phone.matches("[0-9]+"))
            throw new BookstoreException("Author phone number should only contain numbers.");
        if(phone.length() < 10)
            throw new BookstoreException("Author phone number needs to have at least 10 numbers.");
    }
}
