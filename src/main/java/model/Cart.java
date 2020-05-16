package model;

import model.users.Customer;

import java.util.HashMap;


public class Cart{
    private  static Cart thisCart = new Cart();
    private  HashMap<String, Integer> products;
    //Key : productID, Value : count

    private Cart(){
        products = new HashMap<>();
    }

    public  HashMap<String, Integer> getProductIDs() {
        return products;
    }

    public void addProduct(String productID, int count){
        products.put(productID, count);
    }

    public void removeProduct(String productID){
        products.remove(productID);
    }

    public void moveProductsToCustomerCart(Customer customer){
        customer.getCart().putAll(products);
        products.clear();
    }

    public static Cart getThisCart(){
        return thisCart;
    }
}
