package model.log;

import model.Utility;

import java.util.ArrayList;
import java.util.Date;

public class Log {
    private String id;
    private Date date;
    private double cost;
    private double discount;
    private String sellerName;
    private String buyerName;
    private boolean isDelivered;
    private ArrayList<String> productNames;

    public Log(Date date, double cost, double discount,
               ArrayList<String> productNames, String buyerName,
               String sellerName, boolean isDelivered) {
        this.isDelivered = isDelivered;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.date = date;
        this.cost = cost;
        this.discount = discount;
        this.productNames = productNames;
        id = Utility.generateId();
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getId(){
        return id;
    }

    private String getStatus(){
        if(isDelivered){
            return "Delivered";
        } else {
            return "Still not delivered";
        }
    }

    public String toString() {
        String deliveryStatus = getStatus();
        String productNamesInOneString = "Products:\n";
            for(String name: productNames){
            productNamesInOneString = productNamesInOneString + name + "\n";
        }
        return "Log ID: " + id + "\n" +
                "Date: " + date + "\n" +
                "Cost: " + cost + "\n" +
                "Discount" + discount + "\n" +
                "Buyer: " + buyerName + "\n" +
                "Seller: " + sellerName + "\n" +
                "Status: " + deliveryStatus + "\n" +
                productNamesInOneString;
    }

    //TODO: WRITE A LOAD AND A SAVE METHOD FOR THIS CLASS TO PREVENT CREATION OF DUPLICATE OBJECTS IN SELLER AND CUSTOMER
}