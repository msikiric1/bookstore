package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;

import java.util.List;

/**
 * Business logic layer for users
 * @author Muaz Sikiric
 */
public class UserManager {
    public User getUser(String username, String password) throws BookstoreException {
        return DaoFactory.userDao().getUser(username, password);
    }

    public User add(User user) throws BookstoreException {
        return DaoFactory.userDao().add(user);
    }

    public User getById(int id) throws BookstoreException {
        return DaoFactory.userDao().getById(id);
    }

    public List<User> getAll() throws BookstoreException {
        return DaoFactory.userDao().getAll();
    }

    public User update(User user) throws BookstoreException {
        return DaoFactory.userDao().update(user);
    }

    public void delete(int id) throws BookstoreException {
        DaoFactory.userDao().delete(id);
    }

    /**
     * Validates username and password of a new user
     * @param username username (min. 6 characters)
     * @param password password (min. 8 characters)
     * @param confirmPassword repeated password for confirmation (needs to match the first password)
     * @throws UserException
     */
    public void validateRegistration(String username, String password, String confirmPassword) throws UserException {
        if(username == null || username.length() < 6)
            throw new UserException("Username needs to be at least 6 characters.");
        if(password == null || password.length() < 8)
            throw new UserException("Password needs to be at least 8 characters.");
        if(!password.equals(confirmPassword))
            throw new UserException("Passwords do not match.");
    }
}
