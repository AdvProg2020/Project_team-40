package model.properties;

public abstract class Property {
    private String name;

    public String getName() {
        return name;
    }

    public Property(String name) {
        this.name = name;
    }
}
