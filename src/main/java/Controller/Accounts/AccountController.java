package Controller.Accounts;

import model.Cart;
import model.users.User;

public abstract class AccountController{

    public static void login (String username, String password){

    }

    public static Cart getThisCart(){
        return null;
    }

    public static User getThisUser(){
        return null;
    }

    public void editUser(String field, String newField){

    }

    public String getFields(){
        return null;
    }

    public void logout(){
        
    }


}
