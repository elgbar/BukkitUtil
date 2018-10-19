package no.kh498.util;

/**
 * An object with a name and description
 */
public interface Nameable {

    String NAME_PATH = "name";
    String DESCRIPTION_PATH = "description";

    /**
     * @return A description of what this objective is
     */
    default String getDescription() {
        return "";
    }

    /**
     * @return A user friendly string representation of this object
     */
    String getName();

}
