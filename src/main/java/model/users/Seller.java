package model.users;

import model.Off;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class Seller extends User{
    private String companyInfo;
    private HashMap<String, Product> products;
    private HashMap<String, Off> offs;
    private double credit;
    private ArrayList<Log> logs;
    private boolean managerPermission;


    public Seller(String username, String password, String firstName,
                  String lastName, String email, String phoneNo,
                  double credit, String companyInfo) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        this.companyInfo = companyInfo;
        products = new HashMap<>();
        offs = new HashMap<>();
        logs = new ArrayList<>();
    }

    public HashMap<String, Product> getProducts() {
        return products;
    }

    public HashMap<String, Off> getOffs() {
        return offs;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public boolean isManagerPermission() {
        return managerPermission;
    }

    public void addLog(Log log){
        logs.add(log);
    }

    public void addProduct(Product product){
        products.put(product.getProductId(), product);
    }

    public void deleteProduct(Product product){
        products.remove(product.getProductId());
    }

    public boolean doesProductExist(Product product){
        return products.containsValue(product);
    }

    public void addOff(Off off){
        offs.put(off.getId(), off);
    }

    public void deleteOff(Off off){
        offs.remove(off.getId());
    }

    public boolean doesOffExist(Off off){
        return offs.containsValue(off);
    }

    public Product getProductById(String id){
        return products.get(id);
    }

    public Off getOffById(String id){
        return offs.get(id);
    }

    public void increaseCredit(int credit){
        this.credit += credit;
    }

    public void decreaseCredit(int credit){
        this.credit -= credit;
    }

    @Override
    public String toString() {
        return null;
    }
}
