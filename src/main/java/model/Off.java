package model;

import model.enumerations.SetUpStatus;
import model.users.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Off {
    private static HashMap<String, Off> allOffs;
    private ArrayList<String> productIDs;
    private String id;
    private SetUpStatus status;
    private String startDate;
    private String endDate;
    private String seller;
    private int discountPercentage;

    public Off(String startDate, String endDate, int discountPercentage, String seller) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
        this.seller = seller;
        productIDs = new ArrayList<>();
    }

    public void setStatus(SetUpStatus status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(String productID){
        productIDs.add(productID);
    }

    public void removeProduct(String productID){
        productIDs.remove(productID);
    }

    public boolean doesProductExist(String productID){
        return productIDs.contains(productID);
    }

    public ArrayList<String> getProductIDs() {
        return productIDs;
    }

    public String getSeller() {
        return seller;
    }

    public static HashMap<String, Off> getAllOffs() {
        return allOffs;
    }

    public String getId(){
        return this.id;
    }

    public static void addOff(Off off){
        allOffs.put(off.getId(), off);
    }

    public static void loadData(){}

    public static void saveData(){}

}
