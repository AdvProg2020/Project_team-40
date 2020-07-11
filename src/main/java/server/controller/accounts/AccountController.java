package server.controller.accounts;

import exceptions.AccountsException;
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

    public void login(String username, String password) throws AccountsException {
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
        User.setLoggedInUser(tempUser);
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

    public void editUser(String field, String newAmount) {
        if(field.equals("username")) {
            User.getLoggedInUser().setUsername(newAmount);
        } else if(field.equals("password")) {
            User.getLoggedInUser().setPassword(newAmount);
        } else if(field.equals("firstName")) {
            User.getLoggedInUser().setFirstName(newAmount);
        } else if(field.equals("lastName")) {
            User.getLoggedInUser().setLastName(newAmount);
        } else if(field.equals("phoneNumber")) {
            User.getLoggedInUser().setPhoneNo(newAmount);
        } else if(field.equals("email")) {
            User.getLoggedInUser().setEmail(newAmount);
        } else if(User.getLoggedInUser() instanceof Seller) {
            Seller seller = (Seller) User.getLoggedInUser();
            if(field.equals("companyInfo")) {
                seller.setCompanyInfo(newAmount);
            } else if(field.equals("credit")) {
                seller.setCredit(Double.parseDouble(newAmount));
            }
        } else if(User.getLoggedInUser() instanceof Customer) {
            ((Customer) User.getLoggedInUser()).setCredit(Double.parseDouble(newAmount));
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
        User.setLoggedInUser(null);
    }
}
