package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.BookDaoSQLImpl;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

/**
 * Class used for testing BookManager class
 * @author Muaz Sikiric
 */
public class BookManagerTest {
    private BookManager bookManager;
    private BookDaoSQLImpl bookDaoSQLMock;
    private List<Book> books;

    @BeforeEach
    public void setup() {
        bookManager = Mockito.mock(BookManager.class);
        bookDaoSQLMock = Mockito.mock(BookDaoSQLImpl.class);

    }

    @Test
    public void addTest() {

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
}
