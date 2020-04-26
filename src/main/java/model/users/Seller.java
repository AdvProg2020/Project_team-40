package model.users;

import model.Off;
import model.Product;
import model.log.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Seller extends User{
    private String companyInfo;
    private ArrayList<String> productsId;
    private ArrayList<String> offsId;
    private double credit;
    private ArrayList<Log> logs;
    private boolean managerPermission;


    public Seller(String username, String password, String firstName,
                  String lastName, String email, String phoneNo,
                  double credit, String companyInfo) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        this.companyInfo = companyInfo;
        productsId = new ArrayList<>();
        offsId = new ArrayList<>();
        logs = new ArrayList<>();
    }

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    public ArrayList<String> getOffIds() {
        return offsId;
    }

    public double getCredit() {
        return credit;
    }

    public ArrayList<Log> getLogs() {
        return logs;
    }

    public boolean doesHaveManagerPermission() {
        return managerPermission;
    }

    public void addLog(Log log){
        logs.add(log);
    }

    public void addProduct(Product product){
        productsId.add(product.getProductId());
    }

    public void setManagerPermission(boolean managerPermission) {
        this.managerPermission = managerPermission;
    }

    public void deleteProduct(Product product){
        productsId.remove(product.getProductId());
    }

    public boolean doesProductExist(Product product){
        return productsId.contains(product.getProductId());
    }

    public void addOff(Off off){
        offsId.add(off.getId());
    }

    public void deleteOff(Off off){
        offsId.remove(off.getId());
    }

    public boolean doesOffExist(Off off){
        return offsId.contains(off.getId());
    }

    public Product getProductById(String id){
        if(productsId.contains(id)) {
            return Product.getAllProducts().get(id);
        } else {
            return null;
        }
    }

    public Off getOffById(String id){
        if(offsId.contains(id)) {
            return Off.getAllOffs().get(id);
        } else {
            return null;
        }
    }

    public void increaseCredit(int credit){
        this.credit += credit;
    }

    public void decreaseCredit(int credit){
        this.credit -= credit;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " " + lastName + "\n" +
                "Username: " + username + "\n" +
                "Email Address: " + email + "\n" +
                "Phone Number: " + phoneNo + "\n" +
                "Company: " + companyInfo + "\n";
    }
}
