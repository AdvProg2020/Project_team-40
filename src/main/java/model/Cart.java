package model;

import model.users.Customer;

import java.util.HashMap;


public class Cart{
    private static Cart thisCart;
    private static HashMap<String, Integer> products;
    //Key : productID, Value : count

    private Cart(){
        products = new HashMap<>();
    }

    public static HashMap<String, Integer> getProductIDs() {
        return products;
    }

    public static void addProduct(String productID, int count){
        products.put(productID, count);
    }

    public static void removeProduct(String productID){
        products.remove(productID);
    }

    public static void moveProductsToCustomerCart(Customer customer){
        customer.getCart().putAll(products);
        products.clear();
    }

    public static Cart getThisCart(){
        if(thisCart == null)
            thisCart = new Cart();

        return thisCart;
    }
}
