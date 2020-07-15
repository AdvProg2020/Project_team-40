package server.model;

import exceptions.DataException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Auction {
    private static final String PATH = "src/main/resources/auctions/";
    private static HashMap<String, Auction> auctions = new HashMap<>();
    private Date deadline;
    private String productId;
    private int highestPrice;
    private String seller;
    private ArrayList<String> customers = new ArrayList<>();
    private String highestPriceCustomer;
    private String id;
    private String chatId;

    public Auction(Date deadline, String productId, int highestPrice, String seller, String chatId) {
        this.deadline = deadline;
        this.productId = productId;
        this.highestPrice = highestPrice;
        this.seller = seller;
        this.chatId = chatId;
        id = Utility.generateId();
    }

    public static Auction getAuctionById(String id) {
        return auctions.get(id);
    }

    public static HashMap<String, Auction> getAuctions() {
        return auctions;
    }

    public Date getDeadline() {
        return deadline;
    }

    public String getProductId() {
        return productId;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public String getSeller() {
        return seller;
    }

    public String getHighestPriceCustomer() {
        return highestPriceCustomer;
    }

    public String getId() {
        return id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    public void setHighestPriceCustomer(String highestPriceCustomer) {
        this.highestPriceCustomer = highestPriceCustomer;
    }

    public boolean hasReachedDeadline() {
        return deadline.before(new Date());
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
                Auction auction = (Auction) inputStream.readObject();
                auctions.put(auction.getId(), auction);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading chats failed.");
            }
        }
    }

    public static void saveData() throws DataException{
        File directory = new File(PATH);
        if(!directory.exists())
            if(!directory.mkdir())
                throw new DataException("Saving chats failed.");

        for(Map.Entry<String, Auction> entry : auctions.entrySet()) {
            try {
                Auction auction = entry.getValue();
                FileOutputStream file = new FileOutputStream(entry.getKey());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(auction);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving chats failed.");
            }
        }
    }
}
