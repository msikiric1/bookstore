package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

/**
 * Dao interface for User model
 * @author Muaz Sikiric
 */
public interface UserDao extends Dao<User> {
    User getUser(String username, String password) throws BookstoreException;
}
