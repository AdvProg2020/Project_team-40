package model;

import model.users.Customer;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;


public class Cart implements Serializable{
    private static final String PATH = "src/main/resources/";
    private HashMap<String, Integer> products;
    //Key : productID, Value : count

    public Cart(){
        products = new HashMap<>();
    }

    public HashMap<String, Integer> getProductIDs() {
        return products;
    }

    public void addProduct(String productID, int count){
        products.put(productID, count);
    }

    public void removeProduct(String productID){
        products.remove(productID);
    }

    public void moveProductsToCustomerCart(Customer customer){
        //customer.getCart().addAll(temporaryCart.productIDs);
        //temporaryCart.products.clear();
    }
}
