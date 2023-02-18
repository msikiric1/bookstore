package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.AuthorManager;
import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.CategoryManager;
import ba.unsa.etf.rpr.domain.Book;
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
                System.out.println(bookManager.getById(bookId));
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
}
