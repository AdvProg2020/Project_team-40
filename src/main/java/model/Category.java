package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private static HashMap<String, Category> allCategories = new HashMap<>();
    private String name;
    private Category parentCategory;
    private ArrayList<String> productIDs;
    private ArrayList<Category> subCategories;
    private ArrayList<String> extraProperties;

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public static void addCategory(Category category){
        allCategories.put(category.name, category);
    }

    public ArrayList<String> getProductIDs() {
        return productIDs;
    }

    public ArrayList<String> getExtraProperties() {
        return extraProperties;
    }

    public ArrayList<Category> getSubCategories(){
        return subCategories;
    }

    public Category getParentCategory(){
        return parentCategory;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addSubCategory(Category category){
        subCategories.add(category);
    }

    public void addProduct(String productID){
        productIDs.add(productID);
    }

    public boolean hasProduct(String productID){
        return productIDs.contains(productID);
    }

    public void removeProduct(String productID){
        productIDs.remove(productID);
    }

    public static Category getCategoryByName(String name){
        return allCategories.get(name);
    }

    public static void removeCategory(Category category){
        allCategories.remove(category);
    }

    public HashMap<String, Category> getAllCategories() {
        return allCategories;
    }

    public static void loadData(){}

    public static void saveData(){}

}
