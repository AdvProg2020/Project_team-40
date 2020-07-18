package server.model;

import server.model.enumerations.ReceiptTypes;

import java.util.HashMap;

public class Receipt {
    private static HashMap<Integer, Receipt> receipts = new HashMap<>();
    private String username;
    private String description;
    private int origin;
    private int destination;
    private boolean isPaid;
    private ReceiptTypes type;
    private int ID;

    public Receipt(int ID, String username, String description, int origin, int destination, ReceiptTypes type) {
        this.username = username;
        this.description = description;
        this.origin = origin;
        this.destination = destination;
        this.type = type;
        this.ID = ID;
        isPaid = false;
        receipts.put(ID, this);
    }

    public static HashMap<Integer, Receipt> getReceipts() {
        return receipts;
    }

    public static HashMap<Integer, Receipt> getUsersReceipts(String username) {
        HashMap<Integer, Receipt> usersReceipts = new HashMap<>();
        for(Receipt receipt : receipts.values()) {
            if(receipt.getUsername().equals(username)) {
                usersReceipts.put(receipt.ID, receipt);
            }
        }
        return usersReceipts;
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

    public static void loadData() {

    }

    public static void saveData() {

    }
}
