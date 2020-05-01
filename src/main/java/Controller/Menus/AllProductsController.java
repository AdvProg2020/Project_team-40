package Controller.Menus;

import model.Product;

import java.util.ArrayList;

public class AllProductsController{

    private static AllProductsController allProductsController;
    private ArrayList<String> currentFilters;
    private String currentSort;
    private ArrayList<Product> allProductsProccessed;

    public ArrayList<String> getAllCategories(){
        return null;
    }

    public ArrayList<String> getAllSubCategories(String parentName){
        return null;
    }

    public ArrayList<String> getAvailableFilters(){
        return null;
    }

    public void addValuePropertyToFilter(String type, double minValue, double maxValue){

    }

    public void addStringPropertyToFilter(String type, String value){

    }

    public void disableFilter(String type){

    }

    public ArrayList<String> getCurrentFilters(){
        return null;
    }

    public ArrayList<String> getAvailableSorts(){
        return null;
    }

    public void addSort(String type){

    }

    public void disableSort(){

    }

    public String getCurrentSort(){
        return null;
    }

    public Product getProduct(String productID){
        return null;
    }

    public ArrayList<String> getAllProductNames(){
        return null;
    }

    private AllProductsController(){

    }

    public static AllProductsController getInstance(){
        if(allProductsController == null)
            allProductsController = new AllProductsController();

        return allProductsController;
    }
}
