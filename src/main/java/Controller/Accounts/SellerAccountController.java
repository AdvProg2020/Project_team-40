package Controller.Accounts;

import exceptions.AccountsException;
import model.Category;
import model.Off;
import model.Product;
import model.enumerations.SetUpStatus;
import model.log.Log;
import model.users.Seller;
import model.users.User;

import java.util.*;

public class SellerAccountController extends AccountController{
    private static SellerAccountController sellerAccountController;

    private SellerAccountController(){}

    public static SellerAccountController getInstance(){
        if(sellerAccountController == null)
            sellerAccountController = new SellerAccountController();

        return sellerAccountController;
    }

    public void createSellerAccount(String username, String password, String firstName, String lastName, String email,
                                    String phoneNumber, double credit, String companyInfo) throws AccountsException {
        if(User.doesUserExist(username)) {
            throw new AccountsException("User exists with this username.");
        }
        Seller seller = new Seller(username, password, firstName, lastName, email, phoneNumber, credit, companyInfo);
    }

    public void setCompanyInfo(String companyInfo){
        ((Seller) user).setCompanyInfo(companyInfo);
    }

    public ArrayList<Log> getSalesHistory(){
        ArrayList<Log> logs = new ArrayList<>();
        for(String logName: ((Seller)user).getLogsName()) {
            logs.add(Log.getLogById(logName));
        }
        return logs;
    }

    public ArrayList<String> getSellerProductIDs(){
        return ((Seller)user).getProductsId();
    }

    public Product getProductDetails(String productID){
        Product product;
        Seller seller = (Seller) user;
        if(seller.getProductById(productID) == null) {
            return null;
        } else {
            return seller.getProductById(productID);
        }
    }

    public TreeSet<String> getProductBuyers(Product product){
        ArrayList<Log> logs = getSalesHistory();
        TreeSet<String> buyers = new TreeSet<String>();
        for(Log log: logs) {
            for(String productId: log.getProductsId()) {
                if(productId.equals(product.getProductId())){
                    buyers.add(log.getBuyerName());
                }
            }
        }
        return buyers;
    }

    public void createNewProduct(String name, String company, double price, int quantity, String categoryName,
                                 String description){
        Seller seller = (Seller) user;
        Product product = new Product(name, company, price, quantity, seller.getUsername(),
                categoryName);
        product.setExplanation(description);
        Product.addProduct(product);
    }

    public HashMap<String, String> getProductRequiredProperties(String productID){
        return null;
    }

    public void editProduct(String productId, String field, String newField){
        Product product = Product.getProductById(productId);
        if(field.equals("name")) {
            product.setName(newField);
        } else if(field.equalsIgnoreCase("company")) {
            product.setCompany(newField);
        } else if(field.equalsIgnoreCase("price")) {
            product.setPrice(Double.parseDouble(newField));
        } else if(field.equalsIgnoreCase("count")) {
            product.setCount(Integer.parseInt(newField));
        } else if(field.equalsIgnoreCase("category")) {
            product.setCategory(newField);
        } else if(field.equalsIgnoreCase("status")) {
            if(newField.equalsIgnoreCase("production")) {
                product.setStatus(SetUpStatus.Production);
            } else if(newField.equalsIgnoreCase("editing")) {
                product.setStatus(SetUpStatus.Editing);
            } else if(newField.equalsIgnoreCase("confirmed")) {
                product.setStatus(SetUpStatus.Confirmed);
            }
        }
    }

    public void finalizeAddingProduct(String productID){
        //It is used after manager accepted the request
    }

    public void removeProductFromSeller(String productID){

    }

    public HashMap<String, Category> getAllCategories(){
        return Category.getAllCategories();
    }

    public ArrayList<String> getAllOffs(){
        return null;
    }

    public Off getOffDetails(String offID){
        return null;
    }

    public void editOff(String offID, String field, String newField){

    }

    public void removeOff(String offID){

    }

    public void addOffToSeller(ArrayList<String> productIDs, String startDate, String endDate, double percentage){

    }

    public double getBalance(){
        return ((Seller) user).getCredit();
    }
}
