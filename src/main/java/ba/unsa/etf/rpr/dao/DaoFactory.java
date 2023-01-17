package ba.unsa.etf.rpr.dao;

/**
 * Factory class used for singleton implementations of DAO classes
 * @author Muaz Sikiric
 */
public class DaoFactory {
    private static final BookDao bookDao = BookDaoSQLImpl.getInstance();
    private static final AuthorDao authorDao = AuthorDaoSQLImpl.getInstance();
    private static final CategoryDao categoryDao = CategoryDaoSQLImpl.getInstance();

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
