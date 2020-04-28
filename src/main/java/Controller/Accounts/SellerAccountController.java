package Controller.Accounts;

import exceptions.AccountsException;
import model.Category;
import model.Off;
import model.Product;
import model.log.Log;
import model.users.Seller;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerAccountController extends AccountController{
    private static SellerAccountController sellerAccountController;

    public void createSellerAccount(String username, String password, String firstName, String lastName, String email,
                                    String phoneNumber, double credit, String companyInfo) throws AccountsException {
        if(User.doesUserExist(username)) {
            throw new AccountsException("User exists with this username.");
        }
        Seller seller = new Seller(username, password, firstName, lastName, email, phoneNumber, credit, companyInfo);
    }

    public void setCompanyInfo(String CompanyInfo){

    }

    public ArrayList<Log> getSalesHistory(){
        return null;
    }

    public ArrayList<String> getSellerProductIDs(){
        return null;
    }

    public Product getProductDetails(String productID){
        return null;
    }

    public ArrayList<String> getProductBuyers(){
        return null;
    }

    public void addProductToSeller(String productName, int quantity, double price){

    }

    public void createNewProduct(String name, String company, double price, int quantity, String categoryName,
                                 String description){

    }

    public HashMap<String, String> getProductRequiredProperties(String productID){
        return null;
    }

    public void editProduct(String productID, String field, String newField){

    }

    public void finalizeAddingProduct(String productID){

    }

    public void removeProductFromSeller(String productID){

    }

    public ArrayList<String> getAllCategories(){
        return null;
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
        return 0.0;
    }

    private SellerAccountController(){}

    public static SellerAccountController getInstance(){
        if(sellerAccountController == null)
            sellerAccountController = new SellerAccountController();

        return sellerAccountController;
    }
}
