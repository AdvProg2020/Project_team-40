package Controller.Menus;

import model.Product;
import model.properties.Property;

import java.util.ArrayList;

public class OffMenuController{
    private static OffMenuController offMenuController;
    private ArrayList<Property> currentFilters;
    private String currentSort;
    private ArrayList<Product> allProductsProccessed;

    public ArrayList<String> getProductsWithOff(){
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

    public ArrayList<Property> getCurrentFilters(){
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

    private OffMenuController(){

    }

    public static OffMenuController getInstance(){
        if(offMenuController == null)
            offMenuController = new OffMenuController();

        return offMenuController;
    }
}
