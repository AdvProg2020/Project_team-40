package server.model;

import exceptions.DataException;
import server.model.enumerations.ReceiptTypes;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Receipt implements Serializable{
    private static HashMap<Integer, Receipt> receipts = new HashMap<>();
    private static final String PATH = "src/main/resources/receipts/";
    private String username;
    private String description;
    private int origin;
    private int destination;
    private int money;
    private boolean isPaid;
    private ReceiptTypes type;
    private int ID;

    public Receipt(int ID, String username, String description, int money, int origin, int destination, ReceiptTypes type) {
        this.username = username;
        this.description = description;
        this.money = money;
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
            if(receipt.getUsername().equals(username) && !receipt.isPaid()) {
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

    public int getMoney() {
        return money;
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

    public int getID() {
        return ID;
    }

    public static void loadData() throws DataException {
        File directory = new File(PATH);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;
        for (String path : pathNames) {
            try {
                FileInputStream file = new FileInputStream(PATH + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Receipt receipt = (Receipt) inputStream.readObject();
                receipts.put(receipt.getID(), receipt);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading receipts failed.");
            }
        }
    }

    public static void saveData() throws DataException {
        File directory = new File(PATH);
        if(!directory.exists())
            if(!directory.mkdir())
                throw new DataException("Saving receipts failed.");

        for(Map.Entry<Integer, Receipt> entry : receipts.entrySet()) {
            try {
                Receipt receipt = entry.getValue();
                FileOutputStream file = new FileOutputStream(PATH + entry.getKey());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(receipt);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving receipts failed.");
            }
        }
    }
}
