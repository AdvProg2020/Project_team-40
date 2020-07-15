package server.controller.accounts;

import exceptions.AccountsException;
import server.model.Category;
import server.model.Off;
import server.model.Product;
import server.model.enumerations.PropertyType;
import server.model.enumerations.SetUpStatus;
import server.model.log.Log;
import server.model.requests.*;
import server.model.users.Manager;
import server.model.users.Seller;
import server.model.users.User;

import javax.security.auth.login.AccountException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class SellerAccountController extends AccountController{
    private static SellerAccountController sellerAccountController;

    private SellerAccountController(){}

    public static SellerAccountController getInstance(){
        if(sellerAccountController == null)
            sellerAccountController = new SellerAccountController();

        return sellerAccountController;
    }

    public void createSellerAccount(String username, String password, String firstName, String lastName, String email,
                                    String phoneNumber, double credit, String companyInfo) {
        Seller seller = new Seller(username, password, firstName, lastName, email, phoneNumber, credit, companyInfo);
        Manager.addRequest(new SellingPermission(seller.getUsername()));
    }

    public void setCompanyInfo(String username, String companyInfo){
        ((Seller) User.getUserByUsername(username)).setCompanyInfo(companyInfo);
    }

    public String getCompanyInfo(String username) {
        return ((Seller) User.getUserByUsername(username)).getCompanyInfo();
    }

    public ArrayList<Log> getSalesHistory(String username) {
        ArrayList<Log> logs = new ArrayList<>();
        for(String logName: ((Seller)User.getUserByUsername(username)).getLogsName()) {
            logs.add(Log.getLogById(logName));
        }
        return logs;
    }

    public ArrayList<Product> getSellerProducts(String username){
        ArrayList<Product> products = new ArrayList<>();
        for (String id : ((Seller) User.getUserByUsername(username)).getProductsId()) {
            products.add(Product.getProductById(id));
        }
        return products;
    }

    public Product getProductDetails(String username, String productID) throws AccountsException {
        Product product;
        Seller seller = (Seller) User.getUserByUsername(username);
        if(!seller.getProductsId().contains(productID)) {
            throw new AccountsException("Seller doesn't have a product with this ID.");
        } else {
            return seller.getProductById(productID);
        }
    }

    public TreeSet<String> getProductBuyers(String username, String productId) throws AccountsException{
        Seller seller = (Seller) User.getUserByUsername(username);
        if(!seller.getProductsId().contains(productId)) {
            throw new AccountsException("Seller doesn't have a product with this ID.");
        }
        ArrayList<Log> logs = getSalesHistory(username);
        TreeSet<String> buyers = new TreeSet<String>();
        for(Log log: logs) {
            if(log.getProductsId().containsKey(productId)) {
                buyers.add(log.getBuyerName());
            }
        }
        return buyers;
    }

    public Product createNewProduct(String username, String name, String company, double price, int quantity, String categoryName,
                                 String description) throws AccountsException {
        Seller seller = (Seller) User.getUserByUsername(username);
        if(!seller.isManagerPermission()) {
            throw new AccountsException("Seller doesn't have permission.");
        }
        Product product = new Product(name, company, price, quantity, seller.getUsername(), categoryName);
        product.setExplanation(description);
        Manager.addRequest(new AddProduct(product));
        return product;
    }

    public void editProduct(String username, String productId, String field, String newField, HashMap<String, Double>
            extraValueProperties, HashMap<String, String> extraStringProperties) throws AccountsException{
        if(!((Seller)User.getUserByUsername(username)).getProductsId().contains(productId)) {
            throw new AccountsException("Seller doesn't have a product with this ID.");
        }
        Manager.addRequest(new EditProduct(productId, field, newField, extraValueProperties, extraStringProperties));
    }

    public void removeProductFromSeller(String username, String productID) throws AccountsException {
        if(!((Seller)User.getUserByUsername(username)).getProductsId().contains(productID)) {
            throw new AccountsException("Seller doesn't have a product with this ID.");
        }
        Manager.addRequest(new RemoveProduct(productID));
    }

    public void increaseProductsCount(int addedQuantity, String productId) {
        Product product = Product.getProductById(productId);
        product.setCount(product.getCount() + addedQuantity);
    }

    public void decreaseProductCount(int removedQuantity, String productId) throws AccountException {
        Product product = Product.getProductById(productId);
        if(product.getCount() < removedQuantity) {
            throw new AccountException("Number of products is less than your expectation");
        }
        product.setCount(product.getCount() - removedQuantity);
    }

    public HashMap<String, Category> getAllCategories(){
        return Category.getAllCategories();
    }

    public HashMap<String, Off> getAllOffs(String username) {
        HashMap<String, Off> sellersAllOffs = new HashMap<>();
        Seller seller = (Seller) User.getUserByUsername(username);
        for(String offId: seller.getOffIds()) {
            Off off = Off.getOffByID(offId);
            sellersAllOffs.put(off.getId(), off);
        }
        return sellersAllOffs;
    }

    public Off getOffDetails(String username, String offID) throws AccountsException{
        Seller seller = (Seller) User.getUserByUsername(username);
        if(seller.getOffIds().contains(offID)) {
            return Off.getOffByID(offID);
        } else {
            throw new AccountsException("Seller doesn't have an off with this ID.");
        }
    }

    public void editOff(String username, String offID, String field, String newField) throws AccountsException {
        if(!((Seller) User.getUserByUsername(username)).getOffIds().contains(offID)) {
            throw new AccountsException("Seller doesn't have an off with this ID.");
        }
        Manager.addRequest(new EditOff(offID, field, newField));
    }

    public void removeOff(String username, String offID) throws AccountsException {
        if(!((Seller)User.getUserByUsername(username)).getOffIds().contains(offID)) {
            throw new AccountsException("Seller doesn't have an off with this ID.");
        }
        ((Seller) User.getUserByUsername(username)).deleteOff(Off.getOffByID(offID));
        Off.removeOff(offID);
    }

    public void addOffToSeller(String username, ArrayList<String> productIDs, String startDate, String endDate, double percentage)
            throws AccountsException {
        Seller seller = (Seller) User.getUserByUsername(username);
        if(!seller.isManagerPermission()) {
            throw new AccountsException("Seller doesn't have permission.");
        }
        Off off = new Off(startDate, endDate, percentage, seller.getUsername());
        off.addAllProducts(productIDs);
        Manager.addRequest(new AddOff(off));
    }

    public double getBalance(String username){
        return ((Seller) User.getUserByUsername(username)).getCreditInWallet();
    }

    public boolean getHasPermission(String username) {
        return ((Seller) User.getUserByUsername(username)).isManagerPermission();
    }

    public void changeProductsStatus(String productId, SetUpStatus status) {
        Product product = Product.getProductById(productId);
        product.setStatus(status);
    }

    public SetUpStatus getSetUpStatus(String productId) {
        return Product.getProductById(productId).getStatus();
    }

    public HashMap<String, PropertyType> getCategoryProperties(String category) {
        return Category.getCategoryByName(category).getExtraProperties();
    }

    public void setProductsProperties(String propertyName, String value, Product product) {
        product.addExtraProperty(propertyName, value);
    }

    public void setProductsProperties(String propertyName, Double value, Product product) {
        product.addExtraProperty(propertyName, value);
    }
}
