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

    public void validate(Author author) throws BookstoreException {
        if(author.getName() == null || author.getName().length() < 3)
            throw new BookstoreException("Author name needs to be at least 3 characters.");
        if(author.getAddress() == null || author.getAddress().length() < 12)
            throw new BookstoreException("Author address needs to be at least 12 characters.");
        if(author.getPhone() == null || !author.getPhone().matches("[0-9]+"))
            throw new BookstoreException("Author phone number should only contain numbers.");
        if(author.getPhone().length() < 7 || author.getPhone().length() > 15)
            throw new BookstoreException("Author phone number needs to have 7-15 digits.");
    }
}
