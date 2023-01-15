package ba.unsa.etf.rpr.dao;

/**
 * Factory class used for singleton implementations of DAO classes
 * @author Muaz Sikiric
 */
public class DaoFactory {
    private static final BookDao bookDao = new BookDaoSQLImpl();
    private static final AuthorDao authorDao = new AuthorDaoSQLImpl();
    private static final CategoryDao categoryDao = new CategoryDaoSQLImpl();

    private DaoFactory() {}

    public static BookDao bookDao() {
        return bookDao;
    }

    public static AuthorDao authorDao() {
        return authorDao;
    }

    public static CategoryDao categoryDao() {
        return categoryDao;
    }
}
