package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Category {
    private static HashMap<String, Category> allCategories = new HashMap<>();
    private String name;
    private Category parentCategory;
    private ArrayList<Product> products;
    private ArrayList<Category> subCategories;
    private ArrayList<String> extraProperties;

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<String> getExtraProperties() {
        return extraProperties;
    }

    public void addProperty(String name){
        extraProperties.add(name);
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

    public void addProduct(Product product){
        products.add(product);
    }

    public boolean hasProduct(Product product){
        return products.contains(product);
    }

    public void removeProduct(Product product){
        products.remove(product);
    }

    public static Category getCategoryByName(String name){
        return allCategories.get(name);
    }

    public static void addCategory(Category category){
        allCategories.put(category.name, category);
    }

    public static void removeCategory(Category category){
        allCategories.remove(category.name);
    }

    public static HashMap<String, Category> getAllCategories() {
        return allCategories;
    }

    public static void loadData(){}

    public static void saveData(){}

}
