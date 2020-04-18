package model;

import java.util.ArrayList;

public class Category {
    private ArrayList<Category> allCategories;
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

    public void setName(String name) {
        this.name = name;
    }

    public void addSubCategory(Category category){}

    public void addProduct(Product product){}

    public void removeProduct(Product product){}

    public static Category getCategoryByName(String name){return null;}

    public static void addCategory(Category category){}

    public static void removeCategory(Category category){}

    public ArrayList<Category> getAllCategories() {
        return allCategories;
    }

    public boolean doesCategoryExist(Category category){return false;}

    public static void loadData(){}

    public static void saveData(){}

}
