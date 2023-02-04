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
    public List<Book> getAll() throws BookstoreException {
        return DaoFactory.bookDao().getAll();
    }

    public Book getById(int id) throws BookstoreException {
        return DaoFactory.bookDao().getById(id);
    }

    public Book add(Book book) throws BookstoreException {
        return DaoFactory.bookDao().add(book);
    }

    public Book update(Book book) throws BookstoreException {
        return DaoFactory.bookDao().update(book);
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.bookDao().delete(id);
    }

    /**
     * Validates title and publish date of the book
     * @param title book title (min. 4 characters)
     * @param published publish date (needs to be <= current date)
     * @throws BookstoreException
     */
    public void validate(String title, LocalDate published) throws BookstoreException {
        if(title.length() < 4)
            throw new BookstoreException("Title needs to be at least 4 characters.");
        if(LocalDate.now().isBefore(published))
            throw new BookstoreException("Publish date can not be in the future.");
    }
}
