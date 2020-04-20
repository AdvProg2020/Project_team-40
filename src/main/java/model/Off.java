package model;

import model.enumerations.SetUpStatus;
import model.users.Seller;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.DataFormatException;

public class Off {
    private static ArrayList<Off> allOffs;
    private String id;
    private SetUpStatus status;
    private Date startDate;
    private Date endDate;

    private Seller seller;

    private int discountPercentage;
    private ArrayList<Product> products;
    public Off(Date startDate, Date endDate, int discountPercentage, Seller seller) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
        this.seller = seller;
    }

    public void setStatus(SetUpStatus status) {
        this.status = status;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void addProduct(Product product){}

    public void removeProduct(Product product){}

    public boolean doesProductExist(Product product){return false;}

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Product getProductById(String productId){return null;}

    public Seller getSeller() {
        return seller;
    }

    public static ArrayList<Off> getAllOffs() {
        return allOffs;
    }

    public static void addOff(Off off){
        allOffs.add(off);
    }

    public static void loadData(){}

    public static void saveData(){}

}
