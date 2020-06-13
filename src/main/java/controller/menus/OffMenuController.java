package controller.menus;

import exceptions.MenuException;
import interfaces.Filterable;
import interfaces.Sortable;
import model.Category;
import model.Off;
import model.Product;
import model.enumerations.SortTypes;
import model.enumerations.StockStatus;
import model.search.ProductFilter;
import model.search.ProductFilterOld;
import model.search.ProductSort;
import model.search.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class OffMenuController{
    private static OffMenuController offMenuController;

    private ArrayList<Product> products;
    private ArrayList<Product> productsToShow;
    private ProductFilter productFilter;
    private ProductSort productSort;
    private SortTypes currentSort;


    private OffMenuController(){
        products = new ArrayList<Product>();
        products.addAll(Product.getAllProducts().values());

        for (Off off : Off.getAllOffs().values()) {
            products.addAll(off.getProducts());
        }

        productsToShow = products;
        productFilter = new ProductFilter(products);
        productSort = new ProductSort(products, null);

    }


    public static OffMenuController getInstance(){
        if(offMenuController == null)
            offMenuController = new OffMenuController();

        return offMenuController;
    }

    public ArrayList<Product> getProductsWithOff(){
        return productsToShow;
    }

    public Product getProduct(String productID) throws MenuException {
        Product product = Product.getProductById(productID);
        if (product == null)
            throw new MenuException("Product not found.");
        return product;
    }

    public ArrayList<String> getAvailableStringFilters(){
        return (ArrayList<String>) productFilter.getStringProperties().keySet();
    }

    public ArrayList<String> getAvailableValueFilters(){
        return (ArrayList<String>) productFilter.getRangeProperties().keySet();
    }


    public void addFilter(String name, String value) {
        switch(name){
            case "productName" :
                productFilter.setProductName(value);
            case "companyName" :
                productFilter.setCompanyName(value);
            case "sellerName" :
                productFilter.setSellerName(value);
            case "status" :
                if(value.equals("exists"))
                    productFilter.setStatus(StockStatus.EXISTS);
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
        return sorts;
    }

    public void setSort(String sort) throws MenuException {
        if (!getAvailableSorts().contains(sort))
            throw new MenuException("This sort is not available.");
        currentSort = SortTypes.valueOf(sort);
    }

    public void disableSort() {
        currentSort = null;
    }

    public String getCurrentSort(){
        return currentSort.toString();
    }

}
