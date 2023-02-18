package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.CategoryManager;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Author;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Category;
import ba.unsa.etf.rpr.exceptions.BookstoreException;
import org.apache.commons.cli.*;

import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Scanner;

/**
 * CLI (command-line interface)
 * @author Muaz Sikiric
 */
public class AppCLI {
    // options
    private static final Option getBook = new Option("gb", "get-book", true, "Outputs a book");
    private static final Option getBooks = new Option("gbs", "get-books", false, "Outputs all books");
    private static final Option addBook = new Option("ab", "add-book", false, "Adds a new book");
    private static final Option updateBook = new Option("ub", "update-book", true, "Updates a book");
    private static final Option deleteBook = new Option("db", "delete-book", true, "Deletes a book");
    private static final Option getAuthor = new Option("ga", "get-author", true, "Outputs an author");
    private static final Option getAuthors = new Option("gas", "get-authors", false, "Outputs all authors");
    private static final Option addAuthor = new Option("aa", "add-author", false, "Adds a new author");
    private static final Option updateAuthor = new Option("ua", "update-author", true, "Updates an author");
    private static final Option deleteAuthor = new Option("da", "delete-author", true, "Deletes an author");

    // managers
    private static final BookManager bookManager = new BookManager();
    private static final AuthorManager authorManager = new AuthorManager();
    private static final CategoryManager categoryManager = new CategoryManager();

    /**
     * Main function
     * @param args console arguments
     */
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine commandLine = parser.parse(addOptions(), args);

            if (commandLine.hasOption("get-books")) {
                showAllBooks();
            } else if (commandLine.hasOption("get-book")) {
                int bookId = Integer.parseInt(commandLine.getOptionValue("get-book"));
                showBook(bookManager.getById(bookId));
            } else if (commandLine.hasOption("add-book")) {
                Book book = inputBookDetails(null);
                bookManager.add(book);
                System.out.println("Added a new book successfully.");
            } else if (commandLine.hasOption("update-book")) {
                int bookId = Integer.parseInt(commandLine.getOptionValue("update-book"));
                Book book = inputBookDetails(bookManager.getById(bookId));
                bookManager.update(book);
                System.out.println("Updated a book successfully.");
            } else if (commandLine.hasOption("delete-book")) {
                int bookId = Integer.parseInt(commandLine.getOptionValue("delete-book"));
                bookManager.delete(bookId);
                System.out.println("Deleted a book successfully.");
            } else if (commandLine.hasOption("get-authors")) {
                showAllAuthors();
            } else if (commandLine.hasOption("get-author")) {
                int authorId = Integer.parseInt(commandLine.getOptionValue("get-author"));
                showAuthor(authorManager.getById(authorId));
            } else if (commandLine.hasOption("add-author")) {
                Author author = inputAuthorDetails(null);
                authorManager.add(author);
                System.out.println("Added a new author successfully.");
            } else if (commandLine.hasOption("update-author")) {
                int authorId = Integer.parseInt(commandLine.getOptionValue("update-author"));
                Author author = inputAuthorDetails(authorManager.getById(authorId));
                authorManager.update(author);
                System.out.println("Updated an author successfully.");
            } else if (commandLine.hasOption("delete-author")) {
                int authorId = Integer.parseInt(commandLine.getOptionValue("delete-author"));
                authorManager.delete(authorId);
                System.out.println("Deleted an author successfully.");
            } else if (commandLine.hasOption("get-categories")) {
                showAllCategories();
            } else if (commandLine.hasOption("get-category")) {
                int categoryId = Integer.parseInt(commandLine.getOptionValue("get-category"));
                showCategory(categoryManager.getById(categoryId));
            } else if (commandLine.hasOption("add-category")) {
                Category category = inputCategoryDetails(null);
                categoryManager.add(category);
                System.out.println("Added a new category successfully.");
            } else if (commandLine.hasOption("update-category")) {
                int categoryId = Integer.parseInt(commandLine.getOptionValue("update-category"));
                Category category = inputCategoryDetails(categoryManager.getById(categoryId));
                categoryManager.update(category);
                System.out.println("Updated a category successfully.");
            } else if (commandLine.hasOption("delete-category")) {
                int categoryId = Integer.parseInt(commandLine.getOptionValue("delete-category"));
                categoryManager.delete(categoryId);
                System.out.println("Deleted a category successfully.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            printHelp();
            System.exit(-1);
        }
    }

    /**
     * Prints out all the available options
     */
    private static void printHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        PrintWriter printWriter = new PrintWriter(System.out);
        helpFormatter.printUsage(printWriter, 150, "java -jar bookstore-cli-jar-with-dependencies.jar [option] [arg]");
        helpFormatter.printOptions(printWriter, 150, addOptions(), 2, 5);
        printWriter.close();
    }

    /**
     *
     * @return all options
     */
    private static Options addOptions() {
        return new Options().addOption(getBook).addOption(getBooks)
                .addOption(addBook).addOption(updateBook)
                .addOption(deleteBook);
    }

    private static Category inputCategoryDetails(Category defaultCategory) {
        Category category = defaultCategory == null ? new Category() : defaultCategory;
        Scanner in = new Scanner(System.in);

        try {
            System.out.print("Category name: ");
            category.setName(in.nextLine());

            categoryManager.validate(category);
        } catch(Exception e) {
            System.out.println(e.getMessage() + " Try again!");
            return inputCategoryDetails(defaultCategory);
        }
        return category;
    }

    private static Author inputAuthorDetails(Author defaultAuthor) {
        Author author = defaultAuthor == null ? new Author() : defaultAuthor;
        Scanner in = new Scanner(System.in);

        try {
            System.out.print("Author name: ");
            author.setName(in.nextLine());

            System.out.print("Author address: ");
            author.setAddress(in.nextLine());

            System.out.print("Author phone: ");
            author.setPhone(in.nextLine());

            authorManager.validate(author);
        } catch(Exception e) {
            System.out.println(e.getMessage() + " Try again!");
            return inputAuthorDetails(defaultAuthor);
        }
        return author;
    }

    /**
     *
     * @return
     * @throws BookstoreException
     */
    private static Book inputBookDetails(Book defaultBook) {
        Book book = defaultBook == null ? new Book() : defaultBook;
        Scanner in = new Scanner(System.in).useLocale(Locale.US);

        try {
            System.out.print("Book title: ");
            book.setTitle(in.nextLine());

            showAllAuthors();
            System.out.print("Book author id: ");
            book.setAuthor(authorManager.getById(in.nextInt()));

            System.out.print("Book publish date (yyyy-mm-dd): ");
            book.setPublished(LocalDate.parse(in.next()));

            System.out.print("Book price: ");
            book.setPrice(in.nextDouble());

            showAllCategories();
            System.out.print("Book category id: ");
            book.setCategory(categoryManager.getById(in.nextInt()));

            bookManager.validate(book);
        } catch(Exception e) {
            System.out.println(e.getMessage() + " Try again!");
            return inputBookDetails(defaultBook);
        }
        return book;
    }

    /**
     * Prints out all categories
     * @throws BookstoreException
     */
    private static void showAllCategories() throws BookstoreException {
        System.out.println("All categories: ");
        categoryManager.getAll().forEach(category -> {
            System.out.println(" - " + category.getName() + " (id = " + category.getId() + ")");
        });
    }


    private static void showCategory(Category category) {
        System.out.println("Category details: ");
        System.out.println(" - id: " + category.getId());
        System.out.println(" - name: " + category.getName());
    }


    /**
     * Prints out all authors
     * @throws BookstoreException
     */
    private static void showAllAuthors() throws BookstoreException {
        System.out.println("All authors: ");
        authorManager.getAll().forEach(author -> {
            System.out.println(" - " + author.getName() + " (id = " + author.getId() + ")");
        });
    }

    private static void showAuthor(Author author) {
        System.out.println("Author details: ");
        System.out.println("- id: " + author.getId());
        System.out.println(" - name: " + author.getName());
        System.out.println(" - address: " + author.getAddress());
        System.out.println(" - phone: " + author.getPhone());
    }

    /**
     * Prints out all books
     * @throws BookstoreException
     */
    private static void showAllBooks() throws BookstoreException {
        System.out.println("All books: ");
        bookManager.getAll().forEach(book -> {
            System.out.println(" - " + book.getTitle() + " by " + book.getAuthor().getName() + " in '" + book.getCategory().getName() + "' category (id = " + book.getId() + ")");
        });
    }

    private static void showBook(Book book) {
        System.out.println("Book details: ");
        System.out.println(" - id: " + book.getId());
        System.out.println(" - title: " + book.getTitle());
        System.out.println(" - author: " + book.getAuthor().getName());
        System.out.println(" - publish date: " + book.getPublished().toString());
        System.out.println(" - price: " + book.getPrice());
        System.out.println(" - category: " + book.getCategory().getName());
    }
}
