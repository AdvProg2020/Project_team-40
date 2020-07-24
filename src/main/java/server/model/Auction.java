package server.model;

import exceptions.DataException;
import server.model.users.Customer;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Auction {
    private static final String PATH = "src/main/resources/auctions/";
    private static HashMap<String, Auction> auctions = new HashMap<>();
    private static ArrayList<String> onGoingAuctions = new ArrayList<>();
    private Date deadline;
    private String productId;
    private String productName;
    private int highestPrice;
    private String sellerID;
    private String highestPriceCustomer;
    private HashMap<String, Integer> customers;
    private String id;
    private String chatId;

    //TODO: If you had time, Consider count limitation of products
    public Auction(Date deadline, String productId, int highestPrice, String sellerID, String chatId) {
        this.deadline = deadline;
        this.productId = productId;
        this.productName = Product.getProductById(productId).getName();
        this.highestPrice = highestPrice;
        this.highestPriceCustomer = null;
        this.sellerID = sellerID;
        this.chatId = chatId;
        id = Utility.generateId();
        customers = new HashMap<>();
        ((Seller) User.getUserByUsername(sellerID)).addAuction(this);
        auctions.put(id, this);
        onGoingAuctions.add(id);
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

    public String getProductName() {
        return productName;
    }

    public int getHighestPrice() {
        return highestPrice;
    }

    public String getSellerID() {
        return sellerID;
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

    public static ArrayList<Auction> getOnGoingAuctions() {
        ArrayList<Auction> auctions = new ArrayList<>();
        for (String auctionId : onGoingAuctions) {
            auctions.add(Auction.auctions.get(auctionId));
        }
        return auctions;
    }

    public void setHighestPrice(int highestPrice) {
        this.highestPrice = highestPrice;
    }

    public void setHighestPriceCustomer(String highestPriceCustomer) {
        this.highestPriceCustomer = highestPriceCustomer;
        customers.put(highestPriceCustomer, highestPrice);
        for(Map.Entry<String, Integer> customer : customers.entrySet()) {
            if(!customer.getKey().equals(highestPriceCustomer)) {
                Customer buyer = (Customer) User.getUserByUsername(customer.getKey());
                buyer.setCreditInWallet(buyer.getCreditInWallet() + customer.getValue());
            }
        }
        Customer customer = (Customer) User.getUserByUsername(highestPriceCustomer);
        customer.setCreditInWallet(customer.getCreditInWallet() - highestPrice);
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

    public void finish() {
        onGoingAuctions.remove(id);
        if(!customers.isEmpty()) {
            Seller seller = (Seller) User.getUserByUsername(sellerID);
            seller.setCreditInWallet(seller.getCreditInWallet() + Manager.subtractWage(highestPrice));
            Product product = Product.getProductById(productId);
            product.setCount(product.getCount() - 1);
        }
        //TODO: Create log...
    }
}
