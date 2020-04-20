package model.users;

import model.DiscountCode;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Costumer extends User{
    private ArrayList<Product> cart;
    private ArrayList<DiscountCode> discountCodes;
    private double credit;
    private HashMap<String, Log> logs;
    private ArrayList<Product> products;

    public Costumer(String username, String password, String firstName, String lastName,
                    String email, String phoneNo, double credit) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        logs = new HashMap<>();
        products = new ArrayList<>();
        cart = new ArrayList<>();
        discountCodes = new ArrayList<>();
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

    public HashMap<String, Log> getLogs() {
        return logs;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addDiscountCode(DiscountCode discountCode){
        discountCodes.add(discountCode);
    }

    public void addLog(Log purchaseLog){
        logs.put(purchaseLog.getId(), purchaseLog);
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void deleteProduct(Product product){
        products.remove(product);
    }

    public Log getLogById(String id){
        return logs.get(id);
    }

    public double getTotalPriceOfCart() {return 0;
        //TODO: is cart an object in this class or an arrayList of products?
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n";
    }
}
