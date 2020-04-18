package model.users;

import model.DiscountCode;
import model.Product;
import model.log.PurchaseLog;

import java.util.ArrayList;

public class Costumer extends User{
    private ArrayList<Product> cart;
    private ArrayList<DiscountCode> discountCodes;
    private double credit;
    private ArrayList<PurchaseLog> logs;
    private  ArrayList<Product> products;

    public Costumer(String username, String password, String firstName, String lastName,
                    String email, String phoneNo, double credit) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
    }

    public ArrayList<Product> getCart() {
        return cart;
    }

    public ArrayList<DiscountCode> getDiscountCodes() {
        return discountCodes;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<PurchaseLog> getLogs() {
        return logs;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addDiscountCode(DiscountCode discountCode){}

    public void addLog(PurchaseLog purchaseLog){}

    public void addProduct(Product product){}

    public void deleteProduct(Product product){}

    public  PurchaseLog getLogById(String id){return null;}

    public double getTotalPriceOfCart() {return 0;}


    @Override
    public String toString() {
        return null;
    }
}
