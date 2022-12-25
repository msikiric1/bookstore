package ba.unsa.etf.rpr.domain;

import java.util.Date;

/**
 * Bean class for books
 * @author Muaz Sikiric
 */
public class Book {
    private int id;
    private String title;
    private Author author;
    private Date published;
    private Double price;
    private Category category;

    public Book(int id, String title, Author author, Date published, Double price, Category category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
        this.price = price;
        this.category = category;
    }


}
