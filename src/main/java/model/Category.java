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
        parentCategory.addSubCategory(this);
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

    public void addProperty(String property){
        extraProperties.add(property);
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

    private void removeSubCategory(Category category){
        subCategories.remove(category);
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
        for (String productId : category.getProductIDs()) {
            Product.removeProduct(productId);
        }
        category.getParentCategory().removeSubCategory(category);
        allCategories.remove(category.getName());

    }

    public static HashMap<String, Category> getAllCategories() {
        return allCategories;
    }

    public static void loadData(){}

    public static void saveData(){}

}
