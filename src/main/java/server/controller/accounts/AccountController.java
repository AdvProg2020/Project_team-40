package server.controller.accounts;

import exceptions.AccountsException;
import server.model.Cart;
import server.model.users.Customer;
import server.model.users.Seller;
import server.model.users.User;

import java.util.ArrayList;

public class AccountController{
    private static AccountController accountController = new AccountController();

    protected AccountController() {}

    public static AccountController getInstance(){
        return accountController;
    }

    public boolean doesUserExistWithThisUsername(String username) {
        return User.doesUserExist(username);
    }

    public void login(String username, String password) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null) {
            throw new AccountsException("User with this name doesn't exist.");
        }
        if(!user.getPassword().equals(password)) {
            throw new AccountsException("Wrong password");
        }
        if(user instanceof Customer) {
            Cart.getThisCart().moveProductsToCustomerCart((Customer) user);
        }
        user.setOnline(true);
    }

    public void logout(String username){
        User user  = User.getUserByUsername(username);
        user.setOnline(false);
    }

    public boolean isLogin() {
        //TODO: execute proper actions in server
        return false;
    }

    public Cart getThisCart(){
        return Cart.getThisCart();
    }


    public User getUser(String username) throws AccountsException {
        User user = User.getUserByUsername(username);
        if(user == null)
            throw new AccountsException("User not found");
        return user;

    }

    public void editUser(String username, String field, String newAmount) {
        User user = User.getUserByUsername(username);
        if(field.equals("username")) {
            user.setUsername(newAmount);
        } else if(field.equals("password")) {
            user.setPassword(newAmount);
        } else if(field.equals("firstName")) {
            user.setFirstName(newAmount);
        } else if(field.equals("lastName")) {
            user.setLastName(newAmount);
        } else if(field.equals("phoneNumber")) {
            user.setPhoneNo(newAmount);
        } else if(field.equals("email")) {
            user.setEmail(newAmount);
        } else if(user instanceof Seller) {
            Seller seller = (Seller) user;
            if(field.equals("companyInfo")) {
                seller.setCompanyInfo(newAmount);
            } else if(field.equals("credit")) {
                seller.setCreditInWallet(Double.parseDouble(newAmount));
            }
        } else if(user instanceof Customer) {
            ((Customer) user).setCreditInWallet(Double.parseDouble(newAmount));
        }
    }

    public ArrayList<String> getFields(){
        ArrayList<String> fields = new ArrayList<>();
        fields.add("Username");
        fields.add("Password");
        fields.add("First name");
        fields.add("Last name");
        fields.add("Email");
        fields.add("Phone number");
        //TODO:Fix getUserByUsername null argument
        User user = User.getUserByUsername(null);
        if(user instanceof Customer || user instanceof Seller){
            fields.add("credit");
        }
        if(user instanceof Seller) {
            fields.add("Company information");
        }
        return fields;
    }
}
