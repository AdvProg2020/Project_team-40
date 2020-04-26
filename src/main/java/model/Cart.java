package model;

import model.users.Customer;

import java.util.ArrayList;
import java.util.Arrays;

public class Cart {
    private ArrayList<String> products;
    private static Cart temporaryCart;

    private Cart(){
        this.products = new ArrayList<>();
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.add(product.getProductId());
    }

    public void removeProduct(Product product){
        products.remove(product.getProductId());
    }

    public void moveProductsToCustomerCart(Customer customer){
        customer.getCart().addAll(temporaryCart.products);
        temporaryCart.products.clear();
    }

    public static Cart getTemporaryCart(){
        return temporaryCart;
    }

    public static void loadData(){}

    public static void saveData(){}
}
