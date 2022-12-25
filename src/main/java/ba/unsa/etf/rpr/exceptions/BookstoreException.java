package ba.unsa.etf.rpr.exceptions;

public class BookstoreException extends Exception {
    public BookstoreException(String message) {
        super(message);
    }

    public BookstoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
