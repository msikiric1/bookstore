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
    /**
     * Adds an author to the database
     * @param author author to add
     * @return added author
     */
    public Author add(Author author) throws BookstoreException {
        validate(author);
        return DaoFactory.authorDao().add(author);
    }

    /**
     * Returns all authors from the database
     * @return list of all authors
     */
    public List<Author> getAll() throws BookstoreException {
        return DaoFactory.authorDao().getAll();
    }

    /**
     * Returns an author from the database based on it's ID
     * @param id primary key of an author
     * @return author
     */
    public Author getById(int id) throws BookstoreException {
        return DaoFactory.authorDao().getById(id);
    }

    /**
     * Updates an author in the database based on it's ID
     * @param author author to update
     * @return updated author
     */
    public Author update(Author author) throws BookstoreException {
        validate(author);
        return DaoFactory.authorDao().update(author);
    }

    /**
     * Deletes an author from the database base on it's ID
     * @param id primary key of an author
     */
    public void delete(int id) throws BookstoreException {
        DaoFactory.authorDao().delete(id);
    }

    /**
     * Checks if the author satisfies given constraints
     * @param author author
     * @throws BookstoreException
     */
    public void validate(Author author) throws BookstoreException {
        final int minNameLength = 3;
        final int minAddressLength = 12;
        final int minPhoneLength = 7;
        final int maxPhoneLength = 15;

        if(author.getName().length() < minNameLength)
            throw new BookstoreException("Name needs to be at least " + minNameLength + " characters.");
        if(author.getAddress().length() < minAddressLength)
            throw new BookstoreException("Address needs to be at least " + minAddressLength + " characters.");
        if(author.getPhone().length() < minPhoneLength || author.getPhone().length() > maxPhoneLength)
            throw new BookstoreException("Phone number needs to be between " + minPhoneLength + " and " + maxPhoneLength + " digits (inclusive).");
        if(!author.getPhone().matches("[0-9]+"))
            throw new BookstoreException("Phone number should only contain numbers.");
    }
}
