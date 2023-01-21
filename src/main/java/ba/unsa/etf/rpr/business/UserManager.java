package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

public class UserManager {
    public User getUser(String username, String password) throws BookstoreException {
        return DaoFactory.userDao().getUser(username, password);
    }

    public User add(User user) throws BookstoreException {
        return DaoFactory.userDao().add(user);
    }
}
