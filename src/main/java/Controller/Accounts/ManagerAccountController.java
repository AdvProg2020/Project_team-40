package Controller.Accounts;

import exceptions.AccountsException;
import model.Category;
import model.DiscountCode;
import model.requests.Request;
import model.users.Manager;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class ManagerAccountController extends AccountController{

    private static ManagerAccountController managerAccountController;

    public void createManagerAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber) throws AccountsException {
        if(User.doesUserExist(username))
            throw new AccountsException("User exists with this username.");
        new Manager(username, password, firstName, lastName, email, phoneNumber);
    }

    public ArrayList<String> getAllUserNames(){
        return new ArrayList<>(User.getAllUsernames());
    }

    public User getUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found");
        return user;

    }

    public void deleteUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found.");
        User.deleteUser(user);
    }

    public void removeProduct(String productID){

    }

    public void createDisount(String code, String startDate, String endDate, double percentage, double maxDiscount, int countForEachUser,
                              ArrayList<String> listOfUsernames){

    }

    public ArrayList<String> getAllDiscountCodes(){
        return null;
    }

    public DiscountCode getDiscount(String code){
        return null;
    }

    public void editDiscount(String code, String field, String newField){

    }

    public ArrayList<String> getALlRequests(){
        return null;
    }

    public Request getRequest(String requestID){
        return null;
    }

    public void acceptRequest(String requestID){

    }

    public void declineRequest(String requestID){

    }

    public HashMap<String, String> getAllCategories(){
        return null;
    }

    public Category getCategory(String categoryName){
        return null;
    }

    public void addProductToCategory(String categoryName, String productID){

    }

    public void editCategory(String categoryName, String field, String newField){

    }

    public void createCategory(String categoryName){

    }

    public void removeCategory(String categoryName){

    }

    public void addSubCategory(String parentName, String subName){

    }

    public void removeSubCategory(String parentName, String subName){

    }

    private ManagerAccountController(){

    }

    public static ManagerAccountController getInstance(){
        if(managerAccountController == null)
            managerAccountController = new ManagerAccountController();

        return managerAccountController;
    }
}
