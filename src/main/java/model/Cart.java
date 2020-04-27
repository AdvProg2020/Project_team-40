package model;

import model.users.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Cart {
    private HashMap<String, Integer> products;
    private static Cart temporaryCart;

    private Cart(){
        this.products = new HashMap<>();
    }

    public HashMap<String, Integer> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.put(product.getProductId(), 1);
    }

    public void removeProduct(Product product){
        products.remove(product.getProductId());
    }

    //TODO: WRITE THE ADD AND REDUCE QUANTITY OF PRODUCTS METHOD

    public void moveProductsToCustomerCart(Customer customer){
        customer.getCart().putAll(temporaryCart.products);
        temporaryCart.products.clear();
    }

    public static Cart getTemporaryCart(){
        return temporaryCart;
    }

    public static void loadData(){}

    public static void saveData(){}
}
