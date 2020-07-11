package server.controller.accounts;

import exceptions.AccountsException;
import server.AuthenticationTokenHandler;
import server.model.BankAccount;
import server.model.Cart;
import server.model.users.Customer;
import server.model.users.Seller;
import server.model.users.User;

import java.util.ArrayList;
import java.util.Set;

public class AccountController{
    private static AccountController accountController = new AccountController();

    protected AccountController() {}

    public static AccountController getInstance(){
        return accountController;
    }

    public boolean doesUserExistWithThisUsername(String username) {
        return User.doesUserExist(username);
    }

    public String login(String username, String password) throws AccountsException {
        Set<String> usernaems = User.getAllUsernames();
        if(!usernaems.contains(username)) {
            throw new AccountsException("User with this name doesn't exist.");
        }
        User tempUser = User.getUserByUsername(username);
        if(!tempUser.getPassword().equals(password)) {
            throw new AccountsException("Wrong password");
        }
        if(tempUser instanceof Customer) {
            Cart.getThisCart().moveProductsToCustomerCart((Customer) tempUser);
        }
        return AuthenticationTokenHandler.getNewToken();
    }

    public boolean isLogin() {
        return User.isUserLoggedIn();
    }

    public Cart getThisCart(){
        return Cart.getThisCart();
    }

    public User getThisUser(){
        return User.getLoggedInUser();
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
                seller.setCredit(Double.parseDouble(newAmount));
            }
        } else if(user instanceof Customer) {
            ((Customer) user).setCredit(Double.parseDouble(newAmount));
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
        if(User.getLoggedInUser() instanceof Customer || User.getLoggedInUser() instanceof Seller){
            fields.add("credit");
        }
        if(User.getLoggedInUser() instanceof Seller) {
            fields.add("Company information");
        }
        return fields;
    }

    public void setBankAccount(BankAccount bankAccount) throws AccountsException {
        User user = User.getLoggedInUser();
        if(user.getBankAccount() != null)
            throw new AccountsException("You already have an account in this bank!");
        user.setBankAccount(bankAccount);
    }

    public BankAccount getBankAccount() throws AccountsException {
        User user = User.getLoggedInUser();
        if(user.getBankAccount() == null)
            throw new AccountsException("You don't have an account yet!");
        return user.getBankAccount();
    }

    public void logout() {
        //TODO:Update logout implementation
        User.setLoggedInUser(null);
}
}
