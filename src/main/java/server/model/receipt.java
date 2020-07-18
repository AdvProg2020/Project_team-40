package server.model;

import server.model.enumerations.ReceiptTypes;

public class receipt {
    private String username;
    private String description;
    private int origin;
    private int destination;
    private boolean isPaid;
    private ReceiptTypes type;

    public receipt(String username, String description, int origin, int destination, ReceiptTypes type) {
        this.username = username;
        this.description = description;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        isPaid = false;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getUsername() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public int getOrigin() {
        return origin;
    }

    public int getDestination() {
        return destination;
    }

    public ReceiptTypes getType() {
        return type;
    }
}
