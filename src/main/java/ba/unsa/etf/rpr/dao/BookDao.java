package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;

import java.util.Date;
import java.util.List;

/**
 * Dao interface for Book model
 * @author Muaz Sikiric
 */
public interface BookDao extends Dao<Book> {
    /**
     * Return all books that are published between two dates (including them)
     * @param lowerBound first publish date
     * @param upperBound second publish date
     * @return list of books that match the date criteria
     */
    List<Book> getBetweenPublishedDate(Date lowerBound, Date upperBound);
}
