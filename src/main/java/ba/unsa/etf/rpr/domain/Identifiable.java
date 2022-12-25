package ba.unsa.etf.rpr.domain;

/**
 * Interface tha forces all POJO classes that implement it to have setters and getters
 * for id (forcing them to also have id attribute)
 * @author Muaz Sikiric
 */
public interface Identifiable {
    int getId();

    void setId(int id);
}
