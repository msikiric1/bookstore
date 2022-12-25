package ba.unsa.etf.rpr.domain;

/**
 * Interface that forces all POJO classes that implement it to have setters and getters
 * for id (forcing them to also have and id attribute)
 * @author Muaz Sikiric
 */
public interface Identifiable {
    int getId();

    void setId(int id);
}
