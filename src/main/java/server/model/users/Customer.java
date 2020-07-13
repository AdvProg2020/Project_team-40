package server.model.users;

import server.model.DiscountCode;
import server.model.Product;
import server.model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Customer extends User{
    private ArrayList<String> discountCodes;
    private double credit;
    private ArrayList<String> logsId;
    private HashMap<String, Integer> cart;
    //Key Of cart is the products ID, the value is the quantity

    public Customer(){}

    public Customer(String username, String password, String firstName, String lastName,
                    String email, String phoneNo, double credit) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        logsId = new ArrayList<>();
        cart = new HashMap<>();
        discountCodes = new ArrayList<>();
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    public ArrayList<String> getDiscountCodes() {
        return discountCodes;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<String> getLogsId() {
        return logsId;
    }

    public HashMap<String, Integer> getCart() {
        return cart;
    }

    public void addDiscountCode(DiscountCode discountCode){
        discountCodes.add(discountCode.getCode());
    }

    public void addLog(Log purchaseLog){
        logsId.add(purchaseLog.getId());
    }

    public void addProduct(Product product, int quantity){
        cart.put(product.getProductId(), quantity);
    }

    public void addProductsQuantity(String productId) {
        Integer quantity = cart.get(productId);
        cart.remove(productId);
        cart.put(productId, quantity + 1);
    }

    public void decreaseProductsQuantity(String productId) {
        Integer quantity = cart.get(productId);
        cart.remove(productId);
        if(quantity != 1) {
            cart.put(productId, quantity - 1);
        }
    }

    public void deleteProduct(Product product){
        cart.remove(product.getProductId());
    }

    public Log getLogById(String id){
        if(logsId.contains(id)) {
            return Log.getLogById(id);
        } else {
            return null;
        }
    }

    public Product getProductById(String id){
        if(cart.containsKey(id)) {
            return Product.getProductById(id);
        } else {
            return null;
        }
    }

    public void removeAllProducts(){
        cart.clear();
    }

    public double getTotalPriceOfCart() {
        double totalPriceOfCart = 0;
        HashMap<String, Product> allProducts = Product.getAllProducts();
        for(Map.Entry<String, Integer> entry: cart.entrySet()){
            String productId = entry.getKey();
            int quantity = entry.getValue();
            totalPriceOfCart += allProducts.get(productId).getPrice() * quantity;
        }
        return totalPriceOfCart;
    }

    public boolean hasBought(String productID){
        for(String id : logsId) {
            if(Log.getLogById(id).getProductsId().containsKey(productID))
                return true;
        }
        return false;
    }

    public static ArrayList<String> getAllCustomers(){
        ArrayList<String> customers = new ArrayList<>();
        for (String s : allUsers.keySet()) {
            User user = User.getUserByUsername(s);
            if (user instanceof Customer)
                customers.add(s);
        }
        return customers;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n" +
                "Credit: " + credit;
    }
}
