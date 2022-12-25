package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.Date;
import java.util.List;

/**
 * Dao interface for Book model
 * @author Muaz Sikiric
 */
public interface BookDao extends Dao<Book> {
    /**
     * Returns all books that are published between two dates (including them)
     * @param lowerBound first publish date
     * @param upperBound second publish date
     * @return list of books that match the date criteria
     */
    List<Book> getBetweenPublishedDates(Date lowerBound, Date upperBound) throws BookstoreException;

    /**
     * Returns all books from certain category
     * @param category category to search by
     * @return list of books
     */
    List<Book> searchByCategory(Category category) throws BookstoreException;
}
