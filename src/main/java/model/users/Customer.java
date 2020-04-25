package model.users;

import model.DiscountCode;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User{
    private ArrayList<DiscountCode> discountCodes;
    private double credit;
    private HashMap<String, Log> logs;
    private ArrayList<Product> cart;

    public Customer(String username, String password, String firstName, String lastName,
                    String email, String phoneNo, double credit) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        logs = new HashMap<>();
        cart = new ArrayList<>();
        discountCodes = new ArrayList<>();
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

    public ArrayList<Product> getCart() {
        return cart;
    }

    public void addDiscountCode(DiscountCode discountCode){
        discountCodes.add(discountCode);
    }

    public void addLog(Log purchaseLog){
        logs.put(purchaseLog.getId(), purchaseLog);
    }

    public void addProduct(Product product){
        cart.add(product);
    }

    public void deleteProduct(Product product){
        cart.remove(product);
    }

    public Log getLogById(String id){
        return logs.get(id);
    }

    public void removeAllProducts(){
        cart.removeAll(cart);
    }

    public double getTotalPriceOfCart() {
        double totalPriceOfCart = 0;
        for(Product product: cart){
            totalPriceOfCart += product.getPrice();
        }
        return totalPriceOfCart;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n";
    }
}
