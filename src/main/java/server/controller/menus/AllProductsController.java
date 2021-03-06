package server.controller.menus;

import exceptions.AccountsException;
import exceptions.MenuException;
import server.model.Category;
import server.model.Off;
import server.model.Product;
import server.model.enumerations.SortTypes;
import server.model.enumerations.StockStatus;
import server.model.search.ProductFilter;
import server.model.search.ProductSort;
import server.model.search.Range;

import java.util.ArrayList;

public class AllProductsController {

    private static AllProductsController allProductsController;

    private ArrayList<Product> products;
    private ArrayList<Product> productsToShow;
    private ProductFilter productFilter;
    private ProductSort productSort;
    private SortTypes currentSort;

    private AllProductsController(){
        products = new ArrayList<Product>();
        products.addAll(Product.getAllProducts().values());
        productsToShow = products;
        productFilter = new ProductFilter(products);
        productSort = new ProductSort(products, null);
    }

    public static AllProductsController getInstance(){
        if(allProductsController == null)
            allProductsController = new AllProductsController();

        return allProductsController;
    }
    @Deprecated
    public void setIsOffsOnly(boolean offOnly){
        if(offOnly){
            products.clear();
            for (Off off : Off.getAllOffs().values()) {
                products.addAll(off.getProducts());
            }
            productFilter.setProducts(products);
            productsToShow = products;
        }else{
            products.clear();
            products.addAll(Product.getAllProducts().values());
            productFilter.setProducts(products);
            productsToShow = products;
        }
    }
    public ArrayList<Category> getAllCategories(){
        return new ArrayList<>(Category.getAllCategories().values());
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
    @Deprecated
    public void filterAndSort(){
        productFilter.filter();
        productsToShow = productFilter.getFilteredProducts();
        productSort = new ProductSort(productsToShow, currentSort);
        productsToShow = productSort.getSortedProducts();
    }

    @Deprecated
    public ArrayList<Product> getAllProducts(){
        return productsToShow;
    }

    public Product getProduct(String productID) throws MenuException{
        Product product = Product.getProductById(productID);
        if (product == null)
            throw new MenuException("Product not found.");
        return product;
    }

    @Deprecated
    public ArrayList<String> getAvailableStringFilters(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(productFilter.getStringProperties().keySet());
        return arrayList;
    }

    @Deprecated
    public ArrayList<String> getAvailableValueFilters(){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(productFilter.getRangeProperties().keySet());
        return arrayList;
    }

    @Deprecated
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

    @Deprecated
    public void addFilter(String name, ArrayList<String> values) throws MenuException {
        if(!getAvailableStringFilters().contains(name))
            throw new MenuException("This filter is not available.");
        productFilter.setStringProperty(name, values);
    }

    @Deprecated
    public void addFilter(String name, double min, double max) throws MenuException {
        if(!getAvailableValueFilters().contains(name) && !name.equals("price"))
            throw new MenuException("This filter is not available.");
        if(name.equals("price"))
            productFilter.setPrice(new Range(min, max));
        productFilter.setRangeProperty(name, new Range(min, max));
    }

    @Deprecated
    public void disableFilter(String name){
        productFilter.disableFilter(name);
    }

    @Deprecated
    public ArrayList<String> getAvailableSorts(){
        ArrayList<String> sorts = new ArrayList<>();
        sorts.add("MOST_EXPENSIVE");
        sorts.add("CHEAPEST");
        sorts.add("MOST_VISITED");
        sorts.add("HIGHEST_SCORE");
        sorts.add("HIGHEST_SALES");
        return sorts;
    }

    @Deprecated
    public void setSort(String sort) throws MenuException {
        if (!getAvailableSorts().contains(sort))
            throw new MenuException("This sort is not available.");
        currentSort = SortTypes.valueOf(sort);
    }

    @Deprecated
    public double getRangeCap(String rangeProperty) throws MenuException{
        if(!productFilter.getRangeProperties().containsKey(rangeProperty) && !rangeProperty.equals("price"))
            throw new MenuException("No such range property");
        return productFilter.getMaxCap(rangeProperty);
    }

}
