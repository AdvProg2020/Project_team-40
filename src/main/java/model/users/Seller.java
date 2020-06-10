package model.users;

import model.Off;
import model.Product;
import model.log.Log;

import java.util.ArrayList;

public class Seller extends User{
    private String companyInfo;
    private ArrayList<String> productsId;
    private ArrayList<String> offsId;
    private double credit;
    private ArrayList<String> logsName;
    private boolean managerPermission;

    public Seller(String username, String password, String firstName,
                  String lastName, String email, String phoneNo,
                  double credit, String companyInfo) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        this.companyInfo = companyInfo;
        productsId = new ArrayList<>();
        offsId = new ArrayList<>();
        logsName = new ArrayList<>();
    }

    public void setCompanyInfo(String companyInfo) {
        this.companyInfo = companyInfo;
    }

    public void setCredit(double credit) {
        this.credit = credit;
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

    public String getCompanyInfo() {
        return companyInfo;
    }

    public ArrayList<String> getLogsName() {
        return logsName;
    }

    public boolean doesHaveManagerPermission() {
        return managerPermission;
    }

    public void addLog(Log log){
        logsName.add(log.getId());
    }

    public void addProduct(Product product){
        productsId.add(product.getProductId());
    }

    public boolean isManagerPermission() {
        return managerPermission;
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

    public Log getLogById(String id){
        if(logsName.contains(id)) {
            return Log.getLogById(id);
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
    public String getRole() {
        return "Seller";
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
