package Controller.Menus;

import exceptions.AccountsException;
import exceptions.MenuException;
import model.Category;
import model.Product;
import model.enumerations.SortTypes;
import model.search.ProductFilter;
import model.search.ProductSort;
import model.search.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class AllProductsController{

    private static AllProductsController allProductsController;
    private HashMap<String, String> currentStringFilters;
    private HashMap<String, Range> currentIntegerFilters;
    private String currentSort;
    private ArrayList<Product> allProducts;
    private ArrayList<Product> productsToShow;
    private ProductFilter productFilter;
    private ProductSort productSort;

    private AllProductsController(){
        currentStringFilters = new HashMap<>();
        currentIntegerFilters = new HashMap<>();
        allProducts = new ArrayList<>();
        allProducts.addAll(Product.getAllProducts().values());
        productsToShow = allProducts;
        productFilter = null;
        productSort = new ProductSort(productsToShow, null);
    }

    public static AllProductsController getInstance(){
        if(allProductsController == null)
            allProductsController = new AllProductsController();

        return allProductsController;
    }

    public ArrayList<String> getAllCategories(){
        ArrayList<String> allCategories = new ArrayList<>();
        for(Category value : Category.getAllCategories().values()) {
            allCategories.add(value.getName());
        }
        return allCategories;
    }

    public ArrayList<String> getAllSubCategories(String parentName) throws AccountsException{
        ArrayList<String> allSubCategories = new ArrayList<>();

        Category category = Category.getCategoryByName(parentName);
        if(category == null)
            throw new AccountsException("No category with such name exists.");

        for(Category subCategory : category.getSubCategories()) {
            allSubCategories.add(subCategory.getName());
        }

        return allSubCategories;
    }


    public ArrayList<Product> getAllProducts(){
        return productsToShow;
    }

    public Product getProduct(String productID) throws MenuException{
        Product product = Product.getProductById(productID);
        if (product == null)
            throw new MenuException("Product not found.");
        return product;
    }

    public ArrayList<String> getAvailableFilters(){
        return ProductFilter.getAvailableFilters();
    }

    public ArrayList<Product> filter(String name, String value) throws MenuException {
        if (!getAvailableFilters().contains(name))
            throw new MenuException("This filter is not available.");
        currentStringFilters.put(name, value);
        productFilter = ProductFilter.getInstance(allProducts, currentStringFilters, currentIntegerFilters);
        productsToShow = productFilter.getFilter();
        return productsToShow;
    }

    public ArrayList<Product> filter(String name, double min, double max) throws MenuException {
        if (!getAvailableFilters().contains(name))
            throw new MenuException("This filter is not available.");
        currentIntegerFilters.put(name, new Range(min, max));
        productFilter = ProductFilter.getInstance(allProducts, currentStringFilters, currentIntegerFilters);
        productsToShow = productFilter.getFilter();
        return productsToShow;
    }


    public void disableFilter(String selectedField) throws MenuException {
        if (!(currentStringFilters.containsKey(selectedField) || currentIntegerFilters.containsKey(selectedField)))
            throw new MenuException("This field wax not selected.");
        currentStringFilters.remove(selectedField);
        currentIntegerFilters.remove(selectedField);
        productFilter.disableFilter(selectedField);
        productsToShow = productFilter.getFilter();
    }

    public ArrayList<String> getCurrentFilters(){
        ArrayList<String> currentFilters = new ArrayList<>();
        currentFilters.addAll(currentStringFilters.keySet());
        currentFilters.addAll(currentIntegerFilters.keySet());
        return currentFilters;
    }

    public ArrayList<String> getAvailableSorts(){
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("MOST_EXPENSIVE");
        sorts.add("CHEAPEST");
        sorts.add("MOST_VISITED");
        sorts.add("HIGHEST_SCORE");
        return sorts;
    }

    public void addSort(String sort) throws MenuException {
        if (!getAvailableSorts().contains(sort))
            throw new MenuException("This sort is not available.");
        currentSort = sort;
        productSort.setSortType(SortTypes.valueOf(sort));
        productsToShow = productSort.getSortedProducts();
    }

    public void disableSort(){
        currentSort = null;
        productSort.setSortType(null);
        productsToShow = productSort.getSortedProducts();
    }

    public String getCurrentSort(){
        return currentSort;
    }


}
