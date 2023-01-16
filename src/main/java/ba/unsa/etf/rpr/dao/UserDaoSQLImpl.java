package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Class that implements methods from UserDao interface
 * @author Muaz Sikiric
 */
public class UserDaoSQLImpl extends AbstractDao<User> implements UserDao {
    /**
     * Constructor used for connecting to the database
     */
    public UserDaoSQLImpl() { super("users"); }

    @Override
    public User rowToObject(ResultSet rs) throws BookstoreException {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setAdmin(rs.getInt("is_admin") == 1);
            return user;
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> objectToRow(User object) {
        return null;
    }
}
