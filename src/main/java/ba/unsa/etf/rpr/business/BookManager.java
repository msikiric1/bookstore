package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.time.LocalDate;
import java.util.List;

/**
 * Business logic layer for books
 * @author Muaz Sikiric
 */
public class BookManager {
    /**
     * Returns all books from the database
     * @return list of all books
     */
    public List<Book> getAll() throws BookstoreException {
        return DaoFactory.bookDao().getAll();
    }

    /**
     * Returns a book from the database based on it's ID
     * @param id primary key of a book
     * @return book
     */
    public Book getById(int id) throws BookstoreException {
        return DaoFactory.bookDao().getById(id);
    }

    /**
     * Adds a book to the database
     * @param book book to add
     * @return added book
     */
    public Book add(Book book) throws BookstoreException {
        return DaoFactory.bookDao().add(book);
    }

    /**
     * Updates a book in the database based on it's ID
     * @param book book to update
     * @return updated book
     */
    public Book update(Book book) throws BookstoreException {
        return DaoFactory.bookDao().update(book);
    }

    /**
     * Deletes a book from the database base on it's ID
     * @param id primary key of a book
     */
    public void delete(int id) throws BookstoreException {
        DaoFactory.bookDao().delete(id);
    }

    /**
     * Checks if the book satisfies given constraints
     * @param book book
     * @throws BookstoreException
     */
    public void validate(Book book) throws BookstoreException {
        final int minTitleLength = 4;
        final int minPrice = 5;
        final int maxPrice = 50;

        if(book.getTitle().length() < minTitleLength)
            throw new BookstoreException("Title needs to have at least " + minTitleLength + " characters.");
        if(LocalDate.now().isBefore(book.getPublished()))
            throw new BookstoreException("Publish date can not be in the future.");
        if(book.getPrice() < minPrice || book.getPrice() > maxPrice)
            throw new BookstoreException("Price needs to be between $" + minPrice + " and $" + maxPrice + " (inclusive).");
    }
}
