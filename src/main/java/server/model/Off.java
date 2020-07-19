package server.model;

import exceptions.DataException;
import server.model.enumerations.SetUpStatus;
import server.model.users.Seller;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Off implements Serializable{
    private static final String PATH = "src/main/resources/offs/";
    private static HashMap<String, Off> allOffs = new HashMap<>();

    private ArrayList<String> productIDs;

    private String id;
    private SetUpStatus status;
    private String startDate;
    private String endDate;
    private String seller;
    private double discountPercentage;
    public Off(String startDate, String endDate, double discountPercentage, String seller) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
        this.seller = seller;
        this.status = SetUpStatus.Confirmed;
        productIDs = new ArrayList<>();
        id = Utility.generateId();
    }

    public void setStatus(SetUpStatus status) {
        this.status = status;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void makeExpire(){
        for (String productID : productIDs) {
            getSeller().deleteOff(this);
            Product product = Product.getProductById(productID);
            product.setInOff(false);
            product.resetPrice(discountPercentage);
        }
    }


    public void addAllProducts(ArrayList<String> productsId) {
        this.productIDs.addAll(productsId);
        for (String s : productsId) {
            Product product = Product.getProductById(s);
            product.setInOff(true);
            product.setPriceForOff(discountPercentage);

        }
    }

    public void removeProduct(String productID){
        productIDs.remove(productID);
    }

    public boolean doesProductExist(String productID){
        return productIDs.contains(productID);
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for (String productID : productIDs) {
            Product product = Product.getProductById(productID);
            products.add(product);
        }
        return products;
    }

    public SetUpStatus getStatus(){
        return status;
    }

    public String getStartDate(){
        return startDate;
    }

    public String getEndDate(){
        return endDate;
    }

    public double getDiscountPercentage(){
        return discountPercentage;
    }

    public boolean isAvailable(){

        Date now = new Date();
        Date start = new Date();
        Date end = new Date();
        try{
            start = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(startDate);
            end = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(endDate);
        }catch(ParseException e){
          e.printStackTrace();
        }


        return status.equals(SetUpStatus.Confirmed) && now.compareTo(start) > 0 && now.compareTo(end) < 0;
    }

    public Seller getSeller() {
        return (Seller)Seller.getUserByUsername(seller);
    }

    public String getSellerUsername(){
        return seller;
    }

    public ArrayList<String> getProductIDs() {
        return productIDs;
    }

    public String getId(){
        return this.id;
    }

    public static void cleanProduct(String id){
        for (String s : allOffs.keySet()) {
            allOffs.get(s).removeProduct(id);
        }
    }

    public static HashMap<String, Off> getAllOffs() {
        return allOffs;
    }

    private static void activateOff(Off off){
        addOff(off);
        off.getSeller().addOff(off);
    }

    public static void addOff(Off off){
        allOffs.put(off.getId(), off);
    }

    public static void removeOff(String offID){
        allOffs.remove(offID);
    }

    public static Off getOffByID(String offID){
        return allOffs.get(offID);
    }

    private StringBuilder getItemsToShow(ArrayList<String> items) {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String item : items) {
            result.append("  ").append(index).append(". ").append(item).append("\n");
            index++;
        }
        return result;
    }
    @Override
    public String toString() {

        return "ProductIDs: " + "\n" + getItemsToShow(productIDs) +
                "Id: " + id + '\n' +
                "Status: " + status + '\n' +
                "Start Date: " + startDate + '\n' +
                "End Date: " + endDate + '\n' +
                "Seller: " + seller + '\n' +
                "Discount Percentage: " + discountPercentage;
    }

    public static void loadData() throws DataException {
        File directory = new File(PATH);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;
        for (String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(PATH + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Off off = (Off)inputStream.readObject();
                if (off.isAvailable()) {
                    activateOff(off);
                }
                else
                    off.makeExpire();
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading Offs data failed.");
            }

        }
    }

    public static void saveData() throws DataException {
        File directory = new File(PATH);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving Offs data failed.");
        for (Off off : allOffs.values()) {
            try {
                FileOutputStream file = new FileOutputStream(PATH + off.getId());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(off);
                file.close();
                outputStream.close();
            } catch (Exception e) {
                throw new DataException("Saving Offs data failed.");
            }
        }
    }
}
