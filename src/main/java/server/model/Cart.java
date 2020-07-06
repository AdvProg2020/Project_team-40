package server.model;

import exceptions.DataException;
import server.model.users.Customer;

import java.io.*;
import java.util.HashMap;


public class Cart implements Serializable{
    private static final long serialVersionUID = 6840337634955265107L;
    private static final String PATH = "src/main/resources/";
    private static Cart thisCart = new Cart();
    private HashMap<String, Integer> products;
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

    public static void loadData() throws DataException{
        try {
            FileInputStream fileInputStream = new FileInputStream(PATH + "Cart");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            thisCart = (Cart)objectInputStream.readObject();
            fileInputStream.close();
            objectInputStream.close();
        }catch(Exception e){
            saveData();
        }
    }

    public static void saveData() throws DataException{
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(PATH + "Cart");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(getThisCart());
            fileOutputStream.close();
            objectOutputStream.close();
        }catch(Exception e){
            throw new DataException("Saving Cart failed");
        };
    }
}
