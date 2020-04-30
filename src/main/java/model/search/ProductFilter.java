package model.search;

import model.Category;
import model.Product;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductFilter{
    private ArrayList<Product> products;
    private Category category;
    private String productName;
    private String productCompany;
    private String sellerName;
    private SetUpStatus status;
    private Range price;
    private HashMap<String , String> stringProperties;
    private HashMap<String, Range> valueProperties;
    private HashMap<String , Boolean> availableExtraProperties;

    public ProductFilter(ArrayList<Product> products, Category category, String productName, String productCompany,
                         String sellerName, SetUpStatus status, Range price){
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

    private HashMap<String, Boolean> getAvailableExtraProperties(){
        return availableExtraProperties;
    }

    public static ArrayList<String> getAvailableFilters(){
        ArrayList<String> availableFilters = new ArrayList<>();
        availableFilters.add("Name");
        availableFilters.add("Category");
        availableFilters.add("Company");
        availableFilters.add("Seller");
        availableFilters.add("Status");
        availableFilters.add("Price");
        return availableFilters;
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
            if(category != null && !product.getCategory().contains(category.getName())){
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

    public static ProductFilter getInstance(ArrayList<Product> products, HashMap<String, String> stringFilters, HashMap<String,Range> integerFilters){
        Category category = null;
        String productName = null;
        String productCompany = null;
        String sellerName = null;
        SetUpStatus status = null;
        Range range = null;
        for (String filter : stringFilters.keySet()) {
            if (filter.equalsIgnoreCase("name"))
                productName = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("category"))
                category = Category.getCategoryByName(stringFilters.get(filter));
            else if (filter.equalsIgnoreCase("company"))
                productCompany = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("seller"))
                sellerName = stringFilters.get(filter);
            else if (filter.equalsIgnoreCase("status"))
                status = SetUpStatus.valueOf(stringFilters.get(filter));
        }

        for (String filter : integerFilters.keySet()) {
            if (filter.equalsIgnoreCase("price"))
                range = integerFilters.get(filter);
        }

        return new ProductFilter(products, category, productName, productCompany, sellerName, status, range);
    }
}
