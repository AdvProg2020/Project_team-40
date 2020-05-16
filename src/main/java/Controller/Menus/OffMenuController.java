package Controller.Menus;

import exceptions.MenuException;
import model.Off;
import model.Product;
import model.enumerations.SortTypes;
import model.search.ProductFilter;
import model.search.ProductSort;
import model.search.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class OffMenuController implements Interfaces.Sortable, Interfaces.Filterable {
    private static OffMenuController offMenuController;
    private HashMap<String, String> currentStringFilters;
    private HashMap<String, Range> currentIntegerFilters;
    private String currentSort;
    private ArrayList<Product> offedProducts;
    private ArrayList<Product> productsToShow;
    private ProductFilter productFilter;
    private ProductSort productSort;


    private OffMenuController(){
        currentStringFilters = new HashMap<>();
        currentIntegerFilters = new HashMap<>();
        offedProducts = new ArrayList<>();
        for (Off off : Off.getAllOffs().values()) {
            offedProducts.addAll(off.getProducts());
        }
        productsToShow = offedProducts;
        productFilter = null;
        productSort = new ProductSort(productsToShow, null);
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

    @Override
    public ArrayList<String> getAvailableFilters(){
        return ProductFilter.getAvailableFilters();
    }

    @Override
    public ArrayList<Product> filter(String name, String value) throws MenuException {
        if (!getAvailableFilters().contains(name))
            throw new MenuException("This filter is not available.");
        currentStringFilters.put(name, value);
        productFilter = ProductFilter.getInstance(offedProducts, currentStringFilters, currentIntegerFilters);
        productsToShow = productFilter.getFilter();
        return productsToShow;
    }

    @Override
    public ArrayList<Product> filter(String name, double min, double max) throws MenuException {
        if (!getAvailableFilters().contains(name))
            throw new MenuException("This filter is not available.");
        currentIntegerFilters.put(name, new Range(min, max));
        productFilter = ProductFilter.getInstance(offedProducts, currentStringFilters, currentIntegerFilters);
        productsToShow = productFilter.getFilter();
        return productsToShow;
    }

    @Override
    public void disableFilter(String selectedField) throws MenuException {
        if (!(currentStringFilters.containsKey(selectedField) || currentIntegerFilters.containsKey(selectedField)))
            throw new MenuException("This field wax not selected.");
        currentStringFilters.remove(selectedField);
        currentIntegerFilters.remove(selectedField);
        productFilter.disableFilter(selectedField);
        productsToShow = productFilter.getFilter();
    }

    @Override
    public ArrayList<String> getCurrentFilters(){
        ArrayList<String> currentFilters = new ArrayList<>();
        currentFilters.addAll(currentStringFilters.keySet());
        currentFilters.addAll(currentIntegerFilters.keySet());
        return currentFilters;
    }

    @Override
    public ArrayList<String> getAvailableSorts(){
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("MOST_EXPENSIVE");
        sorts.add("CHEAPEST");
        sorts.add("MOST_VISITED");
        sorts.add("HIGHEST_SCORE");
        return sorts;
    }

    @Override
    public ArrayList<Product> addSort(String sort) throws MenuException {
        if (!getAvailableSorts().contains(sort))
            throw new MenuException("This sort is not available.");
        currentSort = sort;
        productSort.setSortType(SortTypes.valueOf(sort));
        productsToShow = productSort.getSortedProducts();
        return productsToShow;
    }

    @Override
    public void disableSort(){
        currentSort = null;
        productSort.setSortType(null);
        productsToShow = offedProducts;
    }

    @Override
    public String getCurrentSort(){
        if (currentSort == null)
            return "No sort.";
        return currentSort;
    }


}
