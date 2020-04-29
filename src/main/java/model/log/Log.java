package model.log;

import model.Utility;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Log implements Serializable {
    private String id;
    private Date date;
    private double cost;
    private double discount;
    private String sellerName;
    private String buyerName;
    private boolean isDelivered;
    private ArrayList<String> productsId;
    private static HashMap<String, Log> allLogs = new HashMap<>();

    public Log(Date date, double cost, double discount,
               ArrayList<String> productsId, String buyerName,
               String sellerName, boolean isDelivered) {
        this.isDelivered = isDelivered;
        this.sellerName = sellerName;
        this.buyerName = buyerName;
        this.date = date;
        this.cost = cost;
        this.discount = discount;
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

    public String getSellerName() {
        return sellerName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public ArrayList<String> getProductsId() {
        return productsId;
    }

    private String getStatus(){
        if(isDelivered){
            return "Delivered";
        } else {
            return "Still not delivered";
        }
    }

    public String toString() {
        String deliveryStatus = getStatus();
        String productNamesInOneString = "Products:\n";
            for(String name: productsId){
            productNamesInOneString = productNamesInOneString + name + "\n";
        }
        return "Log ID: " + id + "\n" +
                "Date: " + date + "\n" +
                "Cost: " + cost + "\n" +
                "Discount" + discount + "\n" +
                "Buyer: " + buyerName + "\n" +
                "Seller: " + sellerName + "\n" +
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