package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.dao.BookDao;
import ba.unsa.etf.rpr.dao.CategoryDao;
import ba.unsa.etf.rpr.dao.CategoryDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muaz Sikiric
 */
public class App {
    public static void main( String[] args ) throws BookstoreException {
        Category category = new Category();
        CategoryDao categoryDao = new CategoryDaoSQLImpl();
        /*
        category.setName("Thriller");
        categoryDao.add(category);
        */
        printCategories(categoryDao);
    }

    public static void printCategories(CategoryDao categoryDao) {
        try {
            List<Category> categories = new ArrayList<>(categoryDao.getAll());
            System.out.println(padRight("Row", 4) + "|" + padRight("Id", 3) + "|Name");
            System.out.println("--------------------------------");
            int i = 1;
            for(Category c : categories) {
                System.out.println(padRight(i, 4) + "|" + padRight(c.getId(), 3) + "|" + c.getName());
                i++;
            }
        } catch(BookstoreException e) {
            System.out.println();
        }
    }

    public static void printBooks(BookDao bookDao) {

    }

    public static String padRight(Object s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public static String padLeft(Object s, int n) {
        return String.format("%" + n + "s", s);
    }
}
