package model.properties;

public class StringProperty extends Property {
    private String value;
    public StringProperty(String name, String value) {
        super(name);
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
