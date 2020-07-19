package client.controller.products_menu;

import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.MenuException;
import server.model.Category;
import server.model.Product;
import server.model.enumerations.SortTypes;
import server.model.enumerations.StockStatus;
import server.model.search.ProductFilter;
import server.model.search.ProductSort;
import server.model.search.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsMenuController {
    private ArrayList<Product> products;
    private ArrayList<Product> productsToShow;
    private ProductFilter productFilter;
    private ProductSort productSort;
    private SortTypes currentSort;
    private HashMap<String, String> requestQueries;
    private static ProductsMenuController controller = new ProductsMenuController();

    private ProductsMenuController(){
        requestQueries = new HashMap<>();
        products = RequestHandler.get("/shop/all_products/", requestQueries, false,
                new TypeToken<ArrayList<Product>>(){}.getType());
        productsToShow = products;
        assert products != null;
        productFilter = new ProductFilter(products);
        productSort = new ProductSort(products, null);
    }

    public void setIsOffsOnly(boolean offOnly){
        if(offOnly){
            products.clear();
            requestQueries.clear();
            ArrayList<Product> productsInOff = RequestHandler.get("/shop/all_offs/products/", requestQueries,
                    false, new TypeToken<ArrayList<Product>>(){}.getType());
            assert productsInOff != null;
            products.addAll(productsInOff);
            productFilter.setProducts(products);
            productsToShow = products;
        }else{
            products.clear();
            requestQueries.clear();
            products = RequestHandler.get("/shop/all_products/", requestQueries, false,
                    new TypeToken<ArrayList<Product>>(){}.getType());
            assert products != null;
            productFilter.setProducts(products);
            productsToShow = products;
        }
    }

    public void filterAndSort(){
        productFilter.filter();
        productsToShow = productFilter.getFilteredProducts();
        productSort = new ProductSort(productsToShow, currentSort);
        productsToShow = productSort.getSortedProducts();
    }

    public ArrayList<Product> getAllProducts(){
        return productsToShow;
    }

    public static ProductsMenuController getInstance() {
        return controller;
    }

    public ArrayList<String> getAvailableStringFilters(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(productFilter.getStringProperties().keySet());
        return arrayList;
    }

    public ArrayList<String> getAvailableValueFilters(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(productFilter.getRangeProperties().keySet());
        return arrayList;
    }

    public void addFilter(String name, String value) {
        switch(name){
            case "productName" :
                productFilter.setProductName(value);
                break;
            case "companyName" :
                productFilter.setCompanyName(value);
                break;
            case "sellerName" :
                productFilter.setSellerName(value);
                break;
            case "status" :
                if(value.equals("exists"))
                    productFilter.setStatus(StockStatus.EXISTS);
                break;
            case "category" :
                productFilter.setCategory(Category.getCategoryByName(value));
                break;
        }
    }

    public void addFilter(String name, ArrayList<String> values) throws MenuException {
        if(!getAvailableStringFilters().contains(name))
            throw new MenuException("This filter is not available.");
        productFilter.setStringProperty(name, values);
    }

    public void addFilter(String name, double min, double max) throws MenuException {
        if(!getAvailableValueFilters().contains(name) && !name.equals("price"))
            throw new MenuException("This filter is not available.");
        if(name.equals("price"))
            productFilter.setPrice(new Range(min, max));
        productFilter.setRangeProperty(name, new Range(min, max));
    }

    public void disableFilter(String name){
        productFilter.disableFilter(name);
    }

    public ArrayList<String> getAvailableSorts(){
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("MOST_EXPENSIVE");
        sorts.add("CHEAPEST");
        sorts.add("MOST_VISITED");
        sorts.add("HIGHEST_SCORE");
        sorts.add("HIGHEST_SALES");
        return sorts;
    }

    public void setSort(String sort) throws MenuException {
        if (!getAvailableSorts().contains(sort))
            throw new MenuException("This sort is not available.");
        currentSort = SortTypes.valueOf(sort);
    }

    public double getRangeCap(String rangeProperty) throws MenuException{
        if(!productFilter.getRangeProperties().containsKey(rangeProperty) && !rangeProperty.equals("price"))
            throw new MenuException("No such range property");
        return productFilter.getMaxCap(rangeProperty);
    }

}
