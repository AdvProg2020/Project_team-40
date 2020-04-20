package model.users;

import model.Off;
import model.Product;
import model.log.Log;

import java.util.ArrayList;

public class Seller extends User{
    private String companyInfo;
    private ArrayList<Product> products;
    private ArrayList<Off> offs;
    private double credit;
    private ArrayList<Log> logs;

    private boolean managerPermission;


    public Seller(String username, String password, String firstName,
                  String lastName, String email, String phoneNo,
                  double credit, String companyInfo) {
        super(username, password, firstName, lastName, email, phoneNo);
        this.credit = credit;
        this.companyInfo = companyInfo;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Off> getOffs() {
        return offs;
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


    public void setManagerPermission(boolean managerPermission) {
        this.managerPermission = managerPermission;
    }

    public void addLog(Log log){}

    public void addProduct(Product product){}

    public void deleteProduct(Product product){}

    public boolean doesProductExist(Product product){return false;}

    public void addOff(Off off){}

    public void deleteOff(Off off){}

    public boolean doesOffExist(Off off){return false;}

    public Product getProductById(String id){return null;}

    public Off getOffById(String id){return null;}

    public void increaseCredit(int credit){}

    public void decreaseCredit(int credit){}

    @Override
    public String toString() {
        return null;
    }
}
