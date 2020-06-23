package model;

import exceptions.DataException;
import model.enumerations.PropertyType;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Category implements Serializable {
    private static final long serialVersionUID = 156158850124079952L;
    private static HashMap<String, Category> allCategories = new HashMap<>();
    private String name;
    private String parentCategoryName;
    private ArrayList<String> productIDs;
    private ArrayList<String> subCategoriesNames;
    private HashMap<String, PropertyType> extraProperties;

    public Category(String name, String parentCategory) {
        this.name = name;
        this.parentCategoryName = parentCategory;
        this.subCategoriesNames = new ArrayList<>();
        this.productIDs = new ArrayList<>();
        this.extraProperties = new HashMap<>();

        if (parentCategory != null) {
            Category.getCategoryByName(parentCategory).addSubCategoryName(name);
            extraProperties.putAll(Category.getCategoryByName(parentCategoryName).getExtraProperties());
        }
    }

    public static void addCategory(Category category){
        allCategories.put(category.name, category);
    }

    public ArrayList<String> getProductIDs() {
        return productIDs;
    }

    public HashMap<String, PropertyType> getExtraProperties() {
        return extraProperties;
    }

    public void addProperty(String propertyName, PropertyType propertyType){
        extraProperties.put(propertyName, propertyType);
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

    public ArrayList<String> getSubCategoriesNames() {
        return subCategoriesNames;
    }

    public Category getParentCategory(){
        return allCategories.get(parentCategoryName);
    }

    public String getParentCategoryName(){
        if (parentCategoryName == null)
            return "-";
        return parentCategoryName;
    }

    public void resetName(String name) {
        Category.getAllCategories().remove(this.name);
                for (String subCategory : subCategoriesNames) {
            allCategories.get(subCategory).parentCategoryName = name;
        }
        for (String productID : productIDs) {
            Product.getProductById(productID).setCategory(name);
        }
        if (parentCategoryName != null) {
            allCategories.get(parentCategoryName).subCategoriesNames.remove(this.name);
            allCategories.get(parentCategoryName).subCategoriesNames.add(name);
        }
        this.name = name;
        Category.addCategory(this);

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

    private StringBuilder getItemsToShow(ArrayList<String> items) {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for (String item : items) {
            result.append("  ").append(index).append(". ").append(item).append("\n");
            index++;
        }
        return result;
    }

    private StringBuilder getItemsToShow(HashMap<String, PropertyType> items) {
        StringBuilder result = new StringBuilder();
        int index = 1;
        for(Map.Entry<String, PropertyType> entry : items.entrySet()) {
            result.append("  ").append(index).append(". ").append(entry.getKey()).append(" - ").append(entry.getValue()).append("\n");
            index++;
        }
        return result;
    }

    @Override
    public String toString() {
        return  "Name: " + name + '\n' +
                "Parent Category Name: " + parentCategoryName + '\n' +
                "ProductIDs: " +'\n'+ getItemsToShow(productIDs) + '\n' +
                "Sub-categories Names: " + '\n' +getItemsToShow(subCategoriesNames)  + '\n' +
                "Extra Properties: " + '\n'  + getItemsToShow(extraProperties);
    }

    public static void loadData() throws DataException {
        String directoryPath = "src/main/resources/categories/";
        File directory = new File(directoryPath);
        String[] pathNames = directory.list();
        if (pathNames == null)
            return;
        for (String path : pathNames) {
            try {
                FileInputStream file = new FileInputStream(directoryPath + path);
                ObjectInputStream inputStream = new ObjectInputStream(file);
                Category category = (Category) inputStream.readObject();
                allCategories.put(category.getName(), category);
                file.close();
                inputStream.close();
                new File(directoryPath + path).delete();
            } catch (Exception e) {
                throw new DataException("Loading categories failed.");
            }

        }
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
