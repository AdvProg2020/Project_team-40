package model;

import model.users.Customer;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> products;
    private static Cart temporaryCart;

    private Cart(){
        this.products = new ArrayList<>();
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(Product product){
        products.add(product);
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
