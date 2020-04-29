package model;

import model.users.Customer;

import java.util.ArrayList;

public class Cart {
    private ArrayList<String> productIDs;
    private static Cart temporaryCart;

    private Cart(){
        this.productIDs = new ArrayList<>();
    }

    public ArrayList<String> getProducts() {
        return productIDs;
    }

    public void addProduct(String productID){
        productIDs.add(productID);
    }

    public void removeProduct(String productID){
        productIDs.add(productID);
    }

    public void moveProductsToCustomerCart(Customer customer){
        //customer.getCart().addAll(temporaryCart.productIDs);
        //temporaryCart.products.clear();
    }

    public static Cart getTemporaryCart(){
        return temporaryCart;
    }

    public static void loadData(){}

    public static void saveData(){}
}
