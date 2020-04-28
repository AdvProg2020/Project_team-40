package Controller.Accounts;

import model.Cart;
import model.users.Customer;
import model.users.Seller;
import model.users.User;

import java.util.ArrayList;

public class AccountController{
    private static AccountController accountController = new AccountController();
    protected User user;

    private AccountController(){}

    public static AccountController getInstance(){
        return accountController;
    }

    public boolean login(String username, String password){
        User user = User.getUserByUsername(username); //TODO: NEEDS TESTING, DOES IT THROW EXCEPTION IF USER DOESNT EXIST?
        if(user == null) {
            return false;
        }
        if(!user.getPassword().equals(password)) {
            return false;
        }
        this.user = user;
        return true;
    }

    public Cart getThisCart(){
        return Cart.getTemporaryCart();
    }

    public User getThisUser(){
        return user;
    }

    public void editUser(String field, String newAmount) {
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
        if(user instanceof Customer || user instanceof Seller){
            fields.add("credit");
        }
        return fields;
    }

    public void logout() {
        user = null;
    }
}
