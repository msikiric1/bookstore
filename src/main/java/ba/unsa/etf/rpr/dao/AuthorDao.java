package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.List;

/**
 * Dao interface for Author model
 * @author Muaz Sikiric
 */
public interface AuthorDao extends Dao<Author> {

    /**
     * Returns list of all authors with the same name as the search parameter (not case-sensitive)
     * @param name name to search authors by (not case-sensitive)
     * @return list of authors
     */
    List<Author> searchByName(String name) throws BookstoreException;
}
