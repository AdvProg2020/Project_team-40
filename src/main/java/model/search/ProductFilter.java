package model.search;

import model.Category;
import model.Product;
import model.enumerations.Status;
import model.users.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFilter{
    private ArrayList<Product> products;
    private Category category;
    private String productName;
    private String productCompany;
    private String sellerName;
    private Status status;
    private Range price;
    private HashMap<String , String> stringProperties;
    private HashMap<String, Range> valueProperties;
    private HashMap<String , Boolean> availableExtraProperties;

    public ProductFilter(ArrayList<Product> products, Category category, String productName, String productCompany,
                         String sellerName, Status status, Range price){
        this.products = products;
        this.category = category;
        this.productName = productName;
        this.productCompany = productCompany;
        this.sellerName = sellerName;
        this.status = status;
        this.price = price;
        stringProperties = new HashMap<>();
        valueProperties = new HashMap<>();
        availableExtraProperties = new HashMap<>();

        for(String extraProperty : category.getExtraProperties()) {
            availableExtraProperties.put(extraProperty, Boolean.FALSE);
        }
    }

    public void addExtraProperty(String name, Range range){
        valueProperties.put(name, range);
        availableExtraProperties.put(name, Boolean.TRUE);
    }

    public void addExtraProperty(String name, String value){
        stringProperties.put(name, value);
        availableExtraProperties.put(name, Boolean.TRUE);
    }

    public HashMap<String, Boolean> getAvailableExtraProperties(){
        return availableExtraProperties;
    }

    public void disableFilter(String name){

        if(name.equals("category")){
            category = null;
        }else if(name.equals("name")){
            productName = null;
        }else if(name.equals("company")){
            productCompany = null;
        }else if(name.equals("seller")){
            sellerName = null;
        }else if(name.equals("status")){
            status = null;
        }else if(name.equals("price")){
            price = null;
        }else if(valueProperties.get(name) != null){
            valueProperties.remove(name);
            availableExtraProperties.put(name, Boolean.FALSE);
        }else if(stringProperties.get(name) != null){
            stringProperties.remove(name);
            availableExtraProperties.put(name, Boolean.FALSE);
        }

    }

    public ArrayList<Product> getFilter(){

        ArrayList<Product> filteredProduct = new ArrayList<>();

        for(Product product : products) {
            if(category != null && !product.getCategory().getName().contains(category.getName())){
                continue;
            }
            if(productName != null && !product.getName().contains(productName)){
                continue;
            }
            if(productCompany != null && !product.getCompany().contains(productCompany)){
                continue;
            }
            if(sellerName != null && (!product.getSeller().getFirstName().contains(sellerName) ||
                    !product.getSeller().getLastName().contains(sellerName)) ){
                continue;
            }
            if(status != null && !product.getStatus().equals(status)){
                continue;
            }
            if(price != null && !price.contains(product.getPrice())){
                continue;
            }
            for(Map.Entry<String, Range> stringRangeEntry : valueProperties.entrySet()) {
                if(!stringRangeEntry.getValue().contains(product.getValueProperty(stringRangeEntry.getKey()))){
                    continue;
                }
            }
            for(Map.Entry<String, String> stringStringEntry : stringProperties.entrySet()) {
                if(!stringStringEntry.getValue().equals(product.getStringProperty(stringStringEntry.getKey()))){
                    continue;
                }
            }
            filteredProduct.add(product);
        }

        return filteredProduct;
    }
}
