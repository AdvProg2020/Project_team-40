package model.log;

import model.Product;
import model.Utility;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Log implements Serializable {
    private String id;
    private Date date;
    private double cost;
    private double costWithoutDiscount;
    private String buyerName;
    private String address;
    private boolean isDelivered;
    private HashMap<String, Integer> productsId;
    private static HashMap<String, Log> allLogs = new HashMap<>();

    public Log(Date date, double cost, double costWithoutDiscount,
               HashMap<String, Integer> productsId, String buyerName, boolean isDelivered) {
        this.isDelivered = isDelivered;
        this.buyerName = buyerName;
        this.date = date;
        this.cost = cost;
        this.costWithoutDiscount = costWithoutDiscount;
        this.productsId = productsId;
        id = Utility.generateId();
        allLogs.put(id, this);
    }

    public static Log getLogById(String id){
        return allLogs.get(id);
    }

    public static HashMap<String, Log> getLogs(){
        return allLogs;
    }

    public void setDelivered(boolean delivered) {
        isDelivered = delivered;
    }

    public String getId(){
        return id;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public Date getDate() {
        return date;
    }

    public double getCostWithoutDiscount() {
        return costWithoutDiscount;
    }

    public double getCost() {
        return cost;
    }

    public String getAddress() {
        return address;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public HashMap<String, Integer> getProductsId() {
        return productsId;
    }

    private String getStatus(){
        if(isDelivered){
            return "Delivered";
        } else {
            return "Still not delivered";
        }
    }

    private String showProductsWithQuantityAndSellers() {
        String productsWithQuantityAndSellers = "Products:\n";
        for(Map.Entry<String, Integer> entry: productsId.entrySet()){
            String id = entry.getKey();
            int quantity = entry.getValue();
            Product product = Product.getProductById(id);
            productsWithQuantityAndSellers = productsWithQuantityAndSellers + "Product: " + product.getName() + "\n";
            productsWithQuantityAndSellers = productsWithQuantityAndSellers + "Seller: " + product.getSeller() + "\n";
            productsWithQuantityAndSellers = productsWithQuantityAndSellers + "Quantity: " + quantity + "\n";
        }
        return productsWithQuantityAndSellers;
    }

    public String toString() {
        String deliveryStatus = getStatus();
        String productNamesInOneString = showProductsWithQuantityAndSellers();
        return "Log ID: " + id + "\n" +
                "Date: " + date + "\n" +
                "Cost: " + cost + "\n" +
                "Discount" + costWithoutDiscount + "\n" +
                "Buyer: " + buyerName + "\n" +
                "Address: " + address + "\n" +
                "Status: " + deliveryStatus + "\n" +
                productNamesInOneString;
    }

    public static void loadData() throws IOException {
        String usersDirectoryPath = "src/main/resources/logs/";
        File logsDirectory = new File(usersDirectoryPath);
        String[] pathNames = logsDirectory.list();
        assert pathNames != null;
        for(String path: pathNames) {
            FileInputStream file = new FileInputStream(usersDirectoryPath + path);
            ObjectInputStream inputStream = new ObjectInputStream(file);
            try {
                Log log = (Log) inputStream.readObject();
                allLogs.put(log.getId(), log);
                file.close();
                inputStream.close();
                new File(usersDirectoryPath + path).delete();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public static void saveData() {
        String logsDirectoryPath = "src/main/resources/logs/";
        for(Map.Entry<String, Log> entry: allLogs.entrySet()) {
            try {
                Log log = entry.getValue();
                FileOutputStream file = new FileOutputStream(logsDirectoryPath + log.getId());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(log);
                file.close();
                outputStream.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}