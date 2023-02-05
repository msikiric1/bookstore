package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import ba.unsa.etf.rpr.exceptions.UserException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.List;

/**
 * Business logic layer for users
 * @author Muaz Sikiric
 */
public class UserManager {
    public User getUser(String username, String password) throws BookstoreException {
        return DaoFactory.userDao().getUser(username, password);
    }

    /**
     * Adds a user to the database
     * @param user user to add
     * @return added user
     */
    public User add(User user) throws BookstoreException {
        return DaoFactory.userDao().add(user);
    }

    /**
     * Returns a user from the database based on it's ID
     * @param id primary key of a user
     * @return user
     */
    public User getById(int id) throws BookstoreException {
        return DaoFactory.userDao().getById(id);
    }

    /**
     * Returns all users from the database
     * @return list of all users
     */
    public List<User> getAll() throws BookstoreException {
        return DaoFactory.userDao().getAll();
    }

    /**
     * Updates a user in the database based on it's ID
     * @param user user to update
     * @return updated user
     */
    public User update(User user) throws BookstoreException {
        return DaoFactory.userDao().update(user);
    }

    /**
     * Deletes a user from the database base on it's ID
     * @param id primary key of a user
     */
    public void delete(int id) throws BookstoreException {
        DaoFactory.userDao().delete(id);
    }

    /**
     * Checks if the user registration details satisfy given constraints
     * @param username username
     * @param password password
     * @param confirmPassword confirm password
     * @throws UserException
     */
    public void validateRegistration(String username, String password, String confirmPassword) throws UserException {
        validateLogin(username, password);

        if(!password.equals(confirmPassword))
            throw new UserException("Passwords do not match.");
    }

    /**
     * Checks if the user login details satisfy given constraints
     * @param username username
     * @param password password
     * @throws UserException
     */
    public void validateLogin(String username, String password) throws UserException {
        final int minUsernameLength = 6;
        final int minPasswordLength = 8;

        if(username.length() < minUsernameLength)
            throw new UserException("Username needs to be at least " + minUsernameLength + " characters.");
        if(password.length() < minPasswordLength)
            throw new UserException("Password needs to be at least " + minPasswordLength + " characters.");
        if(!password.matches("^(?=.*\\d)(?=.*[A-Z]).{8,}$"))
            throw new UserException("Password needs to contain at least one uppercase letter and one number.");
    }

    /**
     * Hashes the given password
     * @param password password to hash
     * @return hashed password
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public String hashPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        String salt = "maven";
        md.update(salt.getBytes());
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new BigInteger(1, hashedPassword).toString(16);
    }
}
