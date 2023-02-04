package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that implements methods from UserDao interface
 * @author Muaz Sikiric
 */
public class UserDaoSQLImpl extends AbstractDao<User> implements UserDao {
    private static UserDaoSQLImpl instance = null;

    /**
     * Constructor used for connecting to the database
     */
    private UserDaoSQLImpl() { super("users"); }

    public static UserDaoSQLImpl getInstance() {
        if(instance == null)
            instance = new UserDaoSQLImpl();
        return instance;
    }

    public static void removeInstance() {
        if(instance != null)
            instance = null;
    }

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
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("username", object.getUsername());
        row.put("password", object.getPassword());
        row.put("is_admin", object.isAdmin());
        return row;
    }

    @Override
    public User getUser(String username, String password) throws BookstoreException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try {
            PreparedStatement stmt = getConnection().prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                User user = rowToObject(rs);
                rs.close();
                return user;
            }
        } catch(SQLException e) {
            throw new BookstoreException(e.getMessage(), e);
        }
        throw new BookstoreException("User not found.");
    }
}
