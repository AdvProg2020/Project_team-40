package model.search;

import model.Category;
import model.Product;
import model.enumerations.PropertyType;
import model.enumerations.SetUpStatus;
import model.enumerations.Status;
import model.enumerations.StockStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class ProductFilter{
    private HashMap<Product, Boolean> products;
    //If true then the product is shown, otherwise its hidden and filtered.

    private Category category;
    private String productName;
    private String sellerName;
    private String companyName;
    private StockStatus status;
    private Range price;

    private HashMap<String, ArrayList<String>> stringProperties;
    private HashMap<String, Range> rangeProperties;

    public ProductFilter(ArrayList<Product> products){

        this.products = new HashMap<>();

        for(Product product : products) {
            this.products.put(product, TRUE);
        }
        
        stringProperties = new HashMap<>();
        rangeProperties = new HashMap<>();
    }

    public void setCategory(Category category){
        //Set category and limit products to this category, if category is null then we are searching among all categories
        this.category = category;

        //Clear previous properties
        stringProperties.clear();
        rangeProperties.clear();

        if(category == null)
            return;

        //Add category specific properties
        for(Map.Entry<String, PropertyType> entry : category.getExtraProperties().entrySet()) {
            if(entry.getValue() == PropertyType.STRING){
                stringProperties.put(entry.getKey(), null);
            }
            if(entry.getValue() == PropertyType.RANGE){
                rangeProperties.put(entry.getKey(), null);
            }
        }
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public void setSellerName(String sellerName){
        this.sellerName = sellerName;
    }

    public void setPrice(Range price){
        this.price = price;
    }

    public void setStatus(StockStatus status){
        this.status = status;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public void setRangeProperty(String propertyName, Range range){
        for(Map.Entry<String, Range> entry : rangeProperties.entrySet()) {
            if(entry.getKey().equals(propertyName))
                entry.setValue(range);
        }
    }

    public void setStringProperty(String propertyName, ArrayList<String> strings){
        for(Map.Entry<String, ArrayList<String>> entry : stringProperties.entrySet()) {
            if(entry.getKey().equals(propertyName))
                entry.setValue(strings);
        }
    }

    public void filter(){
        //Neglect any previous changes on products
        for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
            entry.setValue(TRUE);
        }

        //Manage filters
        if(productName != null){
            for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
                if(!entry.getKey().getName().contains(productName))
                    entry.setValue(FALSE);
            }
        }
        if(sellerName != null){
            for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
                if(!entry.getKey().getSeller().getUsername().contains(sellerName))
                    entry.setValue(FALSE);
            }
        }if(companyName != null){
            for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
                if(!entry.getKey().getCompany().contains(companyName))
                    entry.setValue(FALSE);
            }
        }
        if(status != null){
            for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
                if(status == StockStatus.EXISTS && (entry.getKey().getCount() == 0))
                    entry.setValue(FALSE);
            }
        }
        if(price != null){
            for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
                if(!price.contains(entry.getKey().getPrice()))
                    entry.setValue(FALSE);
            }
        }

        if(category != null){
            for(Map.Entry<String, ArrayList<String>> entry : stringProperties.entrySet()) {
                if(entry.getValue() != null){
                    for(Map.Entry<Product, Boolean> productEntry : products.entrySet()) {
                        if(!entry.getValue().contains(productEntry.getKey().getStringProperty(entry.getKey())))
                            productEntry.setValue(FALSE);
                    }
                }
            }
            for(Map.Entry<String, Range> entry : rangeProperties.entrySet()) {
                if(entry.getValue() != null){
                    for(Map.Entry<Product, Boolean> productEntry : products.entrySet()) {
                        if(!entry.getValue().contains(productEntry.getKey().getValueProperty(entry.getKey())))
                            productEntry.setValue(FALSE);
                    }
                }
            }
        }
    }

    public void disableFilter(String name){

        switch(name){

            case "productName" :
                setProductName(null);
                break;
            case "companyName" :
                setCompanyName(null);
                break;
            case "sellerName" :
                setSellerName(null);
                break;
            case "price" :
                setPrice(null);
                break;
            case "category" :
                setCategory(null);
                break;
            case "status" :
                setStatus(null);
                break;
            default:
                setStringProperty(name, null);
                setRangeProperty(name, null);
        }

    }

    public ArrayList<Product> getFilteredProducts(){
        ArrayList<Product> filteredProducts = new ArrayList<>();

        for(Map.Entry<Product, Boolean> entry : products.entrySet()) {
            if(entry.getValue())
                filteredProducts.add(entry.getKey());
        }

        return filteredProducts;
    }

    public HashMap<String, ArrayList<String>> getStringProperties(){
        return stringProperties;
    }

    public HashMap<String, Range> getRangeProperties(){
        return rangeProperties;
    }
}
