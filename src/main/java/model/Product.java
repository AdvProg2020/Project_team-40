package model;

import model.enumerations.SetUpStatus;
import model.properties.Property;
import model.properties.StringProperty;
import model.properties.ValueProperty;
import model.users.Costumer;
import model.users.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class Product {
    private static HashMap<String, Product> allProducts = new HashMap<>();
    private String productId;
    private SetUpStatus status;
    private String name;
    private String company;
    private double price;
    private int count;
    private Seller seller;
    private Category category;
    private String explanation;
    private ArrayList<Score> allScores;
    private ArrayList<Comment> comments;
    private boolean isInOff;
    private ArrayList<Costumer> allBuyers;
    private ArrayList<Property> extraProperties;
    private int visitCount;

    public Product(String name, String company, double price,
                   int count, Seller seller, Category category) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.count = count;
        this.seller = seller;
        this.category = category;
        this.allBuyers = new ArrayList<>();
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.extraProperties = new ArrayList<>();
        productId = Utility.generateId();
        allProducts.put(productId, this);
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
        return seller;
    }

    public Category getCategory() {
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

    public ArrayList<Property> getExtraProperties() {
        return extraProperties;
    }

    public ArrayList<Costumer> getAllBuyers() {
        return allBuyers;
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

    public void setInOff(boolean inOff) {
        isInOff = inOff;
    }

    public void increaseVisitCount(int visitCount) {
        visitCount++;
    }

    public void addProperty(Property property){
        if(property instanceof ValueProperty)
            extraProperties.add((ValueProperty) property);
        else
            extraProperties.add((StringProperty) property);
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public void addScore(Score score){
        allScores.add(score);
    }

    public static void removeProduct(String id){
        allProducts.remove(id);
    }

    public static Product getProductById(String id){
        return allProducts.get(id);
    }

    public static void loadData(){}

    public static void saveData(){}

}
