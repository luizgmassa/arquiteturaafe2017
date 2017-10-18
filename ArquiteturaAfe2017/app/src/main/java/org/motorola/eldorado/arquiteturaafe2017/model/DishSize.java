package org.motorola.eldorado.arquiteturaafe2017.model;

/**
 * Enum that holds the dish size.
 */
public enum DishSize {
    SMALL("Pequeno"),
    MEDIUM("Médio"),
    LARGE("Grande"),
    EXTRA_LARGE("Extra Grande");

    private String mSize;

    DishSize(String size) {
        mSize = size;
    }

    public String getSize() {
        return mSize;
    }
}
