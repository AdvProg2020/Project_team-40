package model;

import model.enumerations.SetUpStatus;
import model.properties.Property;
import model.users.Costumer;
import model.users.Seller;

import java.util.ArrayList;

public class Product {
    private static ArrayList<Product> allProducts;
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
    private double averageScore;
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
    }

    private void generateId(){}

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCount(int count) {}

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public void setAverageScore(double averageScore) {}

    public void setInOff(boolean inOff) {
        isInOff = inOff;
    }

    public void increaseVisitCount(int visitCount) {}

    public void addProperty(Property property){}

    public void addComment(Comment comment){}

    public void addScore(Score score){}

    @Override
    public String toString() {
        return "Product{}";
    }

    public static void removeProduct(Product product){}

    public static Product getProductById(String id){return null;}

    public static void loadData(){}

    public static void saveData(){}

}
