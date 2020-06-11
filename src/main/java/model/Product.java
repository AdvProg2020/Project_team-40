package model;

import exceptions.DataException;
import model.enumerations.SetUpStatus;
import model.users.Seller;

import java.io.*;
import java.util.*;

public class Product implements Serializable{
    private static final long serialVersionUID = -945372685735934996L;
    private static final String PATH = "src/main/resources/products/";
    private static HashMap<String, Product> allProducts = new HashMap<>();

    private String productId;
    private SetUpStatus status;
    private String name;
    private String company;
    private double basePrice;
    private double price;
    private int count;
    private String seller;
    private String category;
    private String explanation;
    private transient ArrayList<Score> allScores;
    private transient ArrayList<Comment> comments;
    private boolean isInOff;
    private ArrayList<String> allBuyers;
    private HashMap<String, String> extraStringProperties;
    private HashMap<String, Double> extraValueProperties;
    private int visitCount;

    public Product(String name, String company, double price,
                   int count, String seller, String category) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.basePrice = price;
        this.count = count;
        this.seller = seller;
        this.category = category;
        this.allBuyers = new ArrayList<>();
        this.allScores = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.extraStringProperties = new HashMap<>();
        this.extraValueProperties = new HashMap<>();
        this.status = SetUpStatus.Creating;
        productId = Utility.generateId();
    }

    public static void addProduct(Product product){
        allProducts.put(product.productId, product);
    }

    public static HashMap<String, Product> getProductsWithName(String name){
        HashMap<String, Product> result = new HashMap<>();

        for(Map.Entry<String, Product> entry : allProducts.entrySet()) {
            if(entry.getValue().getName().equals(name))
                result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static HashMap<String, Product> getProductsFromSeller(String sellerUsername){
        HashMap<String, Product> result = new HashMap<>();

        for(Map.Entry<String, Product> entry : allProducts.entrySet()) {
            if(entry.getValue().getSeller().getUsername().equals(sellerUsername))
                result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static Product getProductWithSellerAndName(String name, String sellerUsername){

        for(Map.Entry<String, Product> entry : allProducts.entrySet()) {
            if(entry.getValue().getName().equals(name) && entry.getValue().getSeller().getUsername().equals(sellerUsername))
                return entry.getValue();
        }

        return null;
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

    public static Set<String> getAllProductIds(){
        return allProducts.keySet();
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

    public void setExtraStringProperties(HashMap<String, String> extraStringProperties) {
        this.extraStringProperties = extraStringProperties;
    }

    public void setExtraValueProperties(HashMap<String, Double> extraValueProperties) {
        this.extraValueProperties = extraValueProperties;
    }

    public void resetExtraProperty(String property, String newProperty){
        if (property.equalsIgnoreCase("name"))
            setCategory(newProperty);
        else {
            for (String s : extraStringProperties.keySet()) {
                if (s.equals(property)) {
                    extraValueProperties.remove(s);
                    extraStringProperties.put(newProperty, null);
                }
            }
            for (String s : extraValueProperties.keySet()) {
                if (s.equals(property))
                    extraValueProperties.remove(s);
                extraStringProperties.put(newProperty, null);
            }
        }

    }

    public ArrayList<String> getAllBuyers() {
        return allBuyers;
    }

    public void setName(String name) {
        allProducts.remove(this.name);
        this.name = name;
        allProducts.put(name, this);
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

    public void setPriceForOff(double percentage){
        double newPrice = price * (1 - percentage/100);
        setPrice(newPrice);
    }

    public void resetPrice(double percentage){
        double newPrice = Math.round(price * (1 + percentage/100));
        setPrice(newPrice);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public double getAverageScore(){
        double sum = 0.0;
        if (allScores.size() != 0) {
            for (Score score : allScores)
                sum += score.getScore();
            return sum / allScores.size();
        }
        return 0.0;
    }

    public void decreaseNumber(int decreament){
        count -= decreament;
    }

    public void setInOff(boolean inOff) {
        isInOff = inOff;
    }

    public void increaseVisitCount() {
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
        if(allScores == null) {
            allScores = new ArrayList<>();
        }
        allScores.add(score);
    }

    public static void removeProduct(String id){
        Product product = Product.getProductById(id);
        Seller seller = Objects.requireNonNull(Product.getProductById(id)).getSeller();
        seller.getProductsId().remove(id);
        assert product != null;
        Category.getCategoryByName(product.getCategory()).removeProduct(id);
        Off.cleanProduct(id);
        Cart.getThisCart().removeProduct(id);
        allProducts.remove(id);

    }

    public static Product getProductById(String id){
        return allProducts.get(id);
    }

    private StringBuilder getExtraPropertiesToShow(){
        StringBuilder result = new StringBuilder();
        for (String s : extraStringProperties.keySet()) {
            result.append(s).append(": ").append(extraStringProperties.get(s)).append("\n");
        }
        for (String s : extraValueProperties.keySet()) {
            result.append(s).append(": ").append(extraValueProperties.get(s)).append("\n");
        }
        return result;
    }

    public void makeNewArrayList(){
        allScores = new ArrayList<>();
        comments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ProductId :" + productId + '\n' +
                "Status : " + status + '\n' +
                "Name: " + name + '\n' +
                "Company: " + company + '\n' +
                "Seller: " + seller + '\n' +
                "BasePrice: " + basePrice + '\n' +
                "Price: " + price +'\n'+
                "Count: " + count +'\n'+
                "Category: " + category + '\n' +
                getExtraPropertiesToShow() +
                "Explanation: " + explanation + '\n' +
                "VisitCount: " + visitCount + '\n'
                +"AverageScore: " + getAverageScore() + '\n';
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
                Product product = (Product)inputStream.readObject();
                product.makeNewArrayList();
                allProducts.put(product.getProductId(), product);
                file.close();
                inputStream.close();
                new File(PATH + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading products failed.");
            }

        }
    }

    public static void saveData() throws DataException {
        String usersDirectoryPath = "src/main/resources/products/";
        File directory = new File(usersDirectoryPath);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving users failed.");
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
