package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class used for testing CategoryManager class
 * @author Muaz Sikiric
 */
public class CategoryManagerTest {
    private final CategoryManager categoryManager = Mockito.mock(CategoryManager.class);
    private final List<Category> categories = new ArrayList<>();

    @BeforeEach
    public void setup() {
        categories.addAll(Arrays.asList(
                newCategory(1, "Category 1"),
                newCategory(2, "Category 2"),
                newCategory(3, "Category 3")
        ));
    }

    @Test
    public void checkCategoryValidity() throws BookstoreException {
        Category category = newCategory(1, "Test category");
        Mockito.doCallRealMethod().when(categoryManager).validate(Mockito.any());

        Assertions.assertDoesNotThrow(() -> categoryManager.validate(category));

        category.setName("abc");
        BookstoreException e = Assertions.assertThrows(BookstoreException.class, () -> categoryManager.validate(category));
        Assertions.assertEquals(e.getMessage(), "Name needs to be at least 5 characters.");
    }

    @Test
    public void getAllTest() throws BookstoreException {
        Mockito.when(categoryManager.getAll()).thenReturn(categories);

        Assertions.assertEquals(categories, categoryManager.getAll());
        Assertions.assertNotEquals(new ArrayList<>(), categoryManager.getAll());
    }

    @Test
    public void getByIdTest() throws BookstoreException {
        Mockito.doAnswer(answer -> {
            return categories
                    .stream()
                    .filter(element -> element == categories.get((Integer) answer.getArgument(0) - 1))
                    .findFirst()
                    .orElse(null);
        }).when(categoryManager).getById(Mockito.anyInt());

        Assertions.assertEquals(newCategory(2, "Category 2"), categoryManager.getById(2));
        Assertions.assertEquals(newCategory(3, "Category 3"), categoryManager.getById(3));
    }

    @Test
    public void deleteTest() throws BookstoreException {
        Mockito.doAnswer(answer -> {
            // removes element from categories array
            return categories.remove((Integer) answer.getArguments()[0] - 1);
        }).when(categoryManager).delete(Mockito.anyInt());

        categoryManager.delete(1);
        Assertions.assertEquals(categories, Arrays.asList(newCategory(2, "Category 2"), newCategory(3, "Category 3")));
    }

    /**
     * Creates new category with given parameters
     * @return new category
     */
    private Category newCategory(int id, String name) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
}
