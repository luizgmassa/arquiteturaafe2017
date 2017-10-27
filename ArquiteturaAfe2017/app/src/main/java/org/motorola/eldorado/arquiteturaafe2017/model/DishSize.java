package org.motorola.eldorado.arquiteturaafe2017.model;

/**
 * Enum that holds the dish size.
 */
public enum DishSize {
    SMALL("Pequeno"),
    MEDIUM("Médio"),
    LARGE("Grande"),
    EXTRA_LARGE("Extra Grande");

    /**
     * Holds the Dish Size string.
     */
    private String mSize;

    /**
     * Constructor.
     *
     * @param size the dish size.
     */
    DishSize(String size) {
        mSize = size;
    }

    /**
     * Gets the Dish size.
     *
     * @return the dish size string.
     */
    public String getSize() {
        return mSize;
    }
}
