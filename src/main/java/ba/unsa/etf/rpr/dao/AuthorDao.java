package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Author;

import java.util.List;

/**
 * Dao interface for Author model
 * @author Muaz Sikiric
 */
public interface AuthorDao extends Dao<Author> {

    /**
     * Returns list of all authors with the same name as the search parameter
     * @param name name to search authors by
     * @return list of authors
     */
    List<Author> searchByName(String name);
}
