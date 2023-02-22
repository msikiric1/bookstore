package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.BookDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used for testing BookManager class
 * @author Muaz Sikiric
 */
public class BookManagerTest {
    private BookManager bookManager;
    private BookDaoSQLImpl bookDaoSQLMock = Mockito.mock(BookDaoSQLImpl.class);
    private List<Book> books = new ArrayList<>();

    @BeforeEach
    public void setup() {
        bookManager = Mockito.mock(BookManager.class);
        Author author = createAuthor(20, "Test author", "Unknown place", "123456789");
        Category category = createCategory(10, "Test category");
        Book book = createBook(100, "Test book 1", author, LocalDate.now(), 9.99, category);

        books.addAll(Arrays.asList(
                book,
                createBook(101, "Test book 2", author, LocalDate.now().minusMonths(20), 5.99, category)
        ));
    }

    @Test
    public void addBookTest() {

    }

    @Test
    public void checkBookValidity() throws BookstoreException {
        Book book = new Book();
        book.setTitle("Test book");
        book.setPublished(LocalDate.parse("2018-03-17"));
        book.setPrice(9.99);
        Mockito.doCallRealMethod().when(bookManager).validate(book);

        Assertions.assertDoesNotThrow(() -> bookManager.validate(book));

        book.setPrice(4.99);
        BookstoreException e = Assertions.assertThrows(BookstoreException.class, () -> bookManager.validate(book));
        Assertions.assertEquals(e.getMessage(), "Price needs to be between $5 and $50 (inclusive).");

        book.setPublished(LocalDate.now().plusDays(1));
        e = Assertions.assertThrows(BookstoreException.class, () -> bookManager.validate(book));
        Assertions.assertEquals(e.getMessage(), "Publish date can not be in the future.");

        book.setTitle("ok");
        e = Assertions.assertThrows(BookstoreException.class, () -> bookManager.validate(book));
        Assertions.assertEquals(e.getMessage(), "Title needs to have at least 4 characters.");
    }

    private Book createBook(int id, String title, Author author, LocalDate published, Double price, Category category) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublished(published);
        book.setPrice(price);
        book.setCategory(category);
        return book;
    }

    private Author createAuthor(int id, String name, String address, String phone) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setAddress(address);
        author.setPhone(phone);
        return author;
    }

    private Category createCategory(int id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
