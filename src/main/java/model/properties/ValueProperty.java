package model.properties;

import model.Product;

public class ValueProperty extends Property {
    private int value;

    public int getValue() {
        return value;
    }

    public ValueProperty(String name, int value) {
        super(name);
        this.value = value;
    }
}
