package model;

import exceptions.DataException;
import model.enumerations.SetUpStatus;
import model.users.Seller;

import javax.xml.crypto.Data;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Product implements Serializable{

    private static final String PATH = "src/main/resources/products/";
    private static HashMap<String, Product> allProducts = new HashMap<>();
    private String productId;
    private SetUpStatus status;
    private String name;
    private String company;
    private double price;
    private int count;
    private String seller;
    private String category;
    private String explanation;
    private ArrayList<Score> allScores;
    private ArrayList<Comment> comments;
    private boolean isInOff;
    private ArrayList<String> allBuyers;
    private HashMap<String, String> extraStringProperties;
    private HashMap<String, Double> extraValueProperties;
    private int visitCount;

    public Product(String name, String company, double price,
                   int count, String seller, String category) {
        //TODO: DOESNT IT NEED TO SAVE SUBCATEGORY?
        this.name = name;
        this.company = company;
        this.price = price;
        this.count = count;
        this.seller = seller;
        this.category = category;
        this.allBuyers = new ArrayList<>();
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.extraStringProperties = new HashMap<>();
        this.extraValueProperties = new HashMap<>();
        productId = Utility.generateId();
    }

    public static void addProduct(Product product){
        allProducts.put(product.productId, product);
    }

    public String getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public Seller getSeller() {
        return (Seller)Seller.getUserByUsername(seller);
    }

    public String getName(){
        return name;
    }

    public String getCompany(){
        return company;
    }

    public String getExplanation(){
        return explanation;
    }

    public static HashMap<String, Product> getAllProducts(){
        return allProducts;
    }

    public int getVisitCount(){
        return visitCount;
    }

    public SetUpStatus getStatus(){
        return status;
    }

    public String getCategory() {
        return category;
    }

    public ArrayList<Score> getAllScores() {
        return allScores;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public boolean isInOff() {
        return isInOff;
    }

    public HashMap<String, Double> getExtraValueProperties(){
        return extraValueProperties;
    }

    public HashMap<String, String> getExtraStringProperties(){
        return extraStringProperties;
    }

    public String getStringProperty(String name){
        return extraStringProperties.get(name);
    }

    public Double getValueProperty(String name){
        return extraValueProperties.get(name);
    }

    public void resetExtraProperty(String property){
        for (String s : extraStringProperties.keySet()) {
            if (s.equals(property))
                extraStringProperties.replace(s, null);
        }
        for (String s : extraValueProperties.keySet()) {
            if (s.equals(property))
                extraValueProperties.replace(s, null);
        }

    }

    public ArrayList<String> getAllBuyers() {
        return allBuyers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(SetUpStatus status) {
        this.status = status;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public double getAverageScore(){
        double sum = 0.0;
        for(Score score : allScores) {
            sum += score.getScore();
        }
        return sum / allScores.size();
    }

    public void decreaseNumber(int decreament){
        count -= decreament;
    }

    public void setInOff(boolean inOff) {
        isInOff = inOff;
    }

    public void increaseVisitCount(int visitCount) {
        visitCount++;
    }

    public void addExtraProperty(String name, Double value){
        extraValueProperties.put(name, value);
    }

    public void addExtraProperty(String name, String value){
        extraStringProperties.put(name, value);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addScore(Score score){
        allScores.add(score);
    }

    public static void removeProduct(String id){
        Product.getProductById(id).getSeller().getProductsId().remove(id);
        allProducts.remove(id);
    }

    public static Product getProductById(String id){
        if(allProducts.containsKey(id)){
            return allProducts.get(id);
        }else {
            return null;
        }
    }

    public static void loadData() throws DataException {
        File directory = new File(PATH);
        String[] pathNames = directory.list();
        if (pathNames == null)
            throw new DataException("Loading products failed.");
        for (String path: pathNames) {
            try {
                FileInputStream file = new FileInputStream(PATH + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                allProducts.put(((Product)inputStream.readObject()).getProductId(), (Product) inputStream.readObject());
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading products failed.");
            }

        }
    }

    public static void saveData() throws DataException {
        for (Product product : allProducts.values()) {
            try {
                FileOutputStream file = new FileOutputStream(PATH + product.getProductId());
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(product);
                file.close();
                outputStream.close();
            } catch (Exception e) {
                throw new DataException("Saving products failed.");
            }
        }
    }

}
