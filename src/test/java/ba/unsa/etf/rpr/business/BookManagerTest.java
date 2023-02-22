package ba.unsa.etf.rpr.business;

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
    private final BookManager bookManager = Mockito.mock(BookManager.class);
    private final List<Book> books = new ArrayList<>();
    private Book book;
    private Author author;
    private Category category;

    @BeforeEach
    public void setup() throws BookstoreException {
        book = newBook(100, "Test book 1", author, LocalDate.now(), 9.99, category);
        author = newAuthor(20, "Test author 1", "Test address 1", "123456789");
        category = newCategory(10, "Test category 1");

        books.addAll(Arrays.asList(
                book,
                newBook(101, "Test book 2", author, LocalDate.now().minusMonths(20), 5.99, category),
                newBook(102, "Test book 3", newAuthor(21, "Test author 2", "Test address 2", "987654321"), LocalDate.now().minusMonths(100), 23.99, newCategory(11, "Test Category 2"))
        ));
    }

    @Test
    public void checkBookValidity() throws BookstoreException {
        Mockito.doCallRealMethod().when(bookManager).validate(Mockito.any());
        Book book = newBook(1, "Test book", author, LocalDate.parse("2018-03-17"), 9.99, category);

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

    @Test
    public void addTest() throws BookstoreException {
        Mockito.doAnswer(answer -> {
            books.add(answer.getArgument(0));
            return answer.getArgument(0);
        }).when(bookManager).add(Mockito.any());

        List<Book> previousBooks = new ArrayList<>(books);
        Book addedBook = bookManager.add(book);

        previousBooks.add(addedBook);
        Assertions.assertEquals(books, previousBooks);
    }

    @Test
    public void getAllTest() throws BookstoreException {
        Mockito.when(bookManager.getAll()).thenReturn(books);

        Assertions.assertEquals(books, bookManager.getAll());
    }

    @Test
    public void updateTest() throws BookstoreException {
        Mockito.doAnswer(answer -> {
            Book previousBook = books.stream().filter(book -> {
                return book.getId() == ((Book) answer.getArgument(0)).getId();
            }).findFirst().orElse(null);
            books.set(books.indexOf(previousBook), answer.getArgument(0));
            return answer.getArgument(0);
        }).when(bookManager).update(Mockito.any());

        Book updatedBook = bookManager.update(book);

        Assertions.assertEquals(book, updatedBook);
    }

    /**
     * Creates a new book with given parameters
     * @return new book
     */
    private Book newBook(int id, String title, Author author, LocalDate published, Double price, Category category) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublished(published);
        book.setPrice(price);
        book.setCategory(category);
        return book;
    }

    /**
     * Creates a new author with given parameters
     * @return new author
     */
    private Author newAuthor(int id, String name, String address, String phone) {
        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setAddress(address);
        author.setPhone(phone);
        return author;
    }

    /**
     * Creates a new category with given parameters
     * @return new category
     */
    private Category newCategory(int id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
