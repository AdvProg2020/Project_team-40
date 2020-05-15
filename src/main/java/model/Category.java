package model;

import exceptions.DataException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {
    private static HashMap<String, Category> allCategories = new HashMap<>();
    private String name;
    private String parentCategoryName;
    private ArrayList<String> productIDs;
    private ArrayList<String> subCategoriesNames;
    private ArrayList<String> extraProperties;

    public Category(String name, String parentCategory) {
        this.name = name;
        this.parentCategoryName = parentCategory;
        this.subCategoriesNames = new ArrayList<>();
        this.productIDs = new ArrayList<>();
        this.extraProperties = new ArrayList<>();
        if (parentCategory != null)
            Category.getCategoryByName(parentCategory).addSubCategoryName(name);
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
        ArrayList<Category> subCategories = new ArrayList<>();
        for(Map.Entry<String, Category> entry: allCategories.entrySet()) {
            if(subCategoriesNames.contains(entry.getKey())) {
                subCategories.add(entry.getValue());
            }
        }
        return subCategories;
    }

    public Category getParentCategory(){
        return allCategories.get(parentCategoryName);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addSubCategoryName(String categoryName){
        subCategoriesNames.add(categoryName);
    }

    public void addProduct(String productID){
        productIDs.add(productID);
    }

    private void removeSubCategory(String category){
        subCategoriesNames.remove(category);
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
        Category parent =category.getParentCategory();
        if (parent != null)
            parent.removeSubCategory(category.getName());
        for (Category subCategory : category.getSubCategories()) {
            removeCategory(subCategory);
        }
        allCategories.remove(category.getName());
    }

    public static HashMap<String, Category> getAllCategories() {
        return allCategories;
    }

    public static void loadData(){

    }

    public static void saveData() throws DataException {
        String path = "src/main/resources/categories/";
        File directory = new File(path);
        if (!directory.exists())
            if (!directory.mkdir())
                throw new DataException("Saving categories failed.");
        for (Map.Entry<String, Category> entry : allCategories.entrySet()) {
            try {
                Category category = entry.getValue();
                FileOutputStream file = new FileOutputStream(path + category.name);
                ObjectOutputStream outputStream = new ObjectOutputStream(file);
                outputStream.writeObject(category);
                file.close();
                outputStream.close();

            } catch (Exception e) {
                throw new DataException("Saving categories failed.");
            }
        }
    }

}
