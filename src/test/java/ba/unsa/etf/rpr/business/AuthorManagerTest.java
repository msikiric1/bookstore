package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used for testing AuthorManager class
 * @author Muaz Sikiric
 */
public class AuthorManagerTest {
    private final AuthorManager authorManager = Mockito.mock(AuthorManager.class);
    private final List<Author> authors = new ArrayList<>();

    @BeforeEach
    void setup() throws BookstoreException {
        authors.addAll(Arrays.asList(
                newAuthor(1, "Author 1", "Address 1", "111111111"),
                newAuthor(2, "Author 2", "Address 2", "222222222"),
                newAuthor(3, "Author 3", "Address 3", "333333333")
        ));
        Mockito.doCallRealMethod().when(authorManager).validate(Mockito.any());

    }

    @Test
    public void checkAuthorValidity() {
        Author author = newAuthor(10, "Test author", "Unknown place", "123456789");
        Assertions.assertDoesNotThrow(() -> authorManager.validate(author));

        author.setPhone("123-456-789");
        BookstoreException e = Assertions.assertThrows(BookstoreException.class, () -> authorManager.validate(author));
        Assertions.assertEquals(e.getMessage(), "Phone number should only contain numbers.");
    }

    @Test
    public void addTest() throws BookstoreException {
        Mockito.doAnswer(answer -> {
            authors.add(answer.getArgument(0));
            return answer.getArgument(0);
        }).when(authorManager).add(Mockito.any());

        List<Author> previousAuthors = new ArrayList<>();
        previousAuthors.addAll(authors);
        Author addedAuthor = authorManager.add(newAuthor(4, "Author 4", "Address 4", "444444444"));

        previousAuthors.add(addedAuthor);
        Assertions.assertEquals(authors, previousAuthors);
    }

    @Test
    public void getAllTest() throws BookstoreException {
        Mockito.when(authorManager.getAll()).thenReturn(authors);

        Assertions.assertEquals(authors, authorManager.getAll());
    }

    /**
     * Creates new author with given parameters
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
}
