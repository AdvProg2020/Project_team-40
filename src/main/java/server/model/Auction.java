package server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Auction {
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

    public boolean hasReachedDeadline() {
        return deadline.before(new Date());
    }


}
