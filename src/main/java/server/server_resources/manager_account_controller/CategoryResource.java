package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.ManagerAccountController;

public class CategoryResource extends ServerResource {
    @Post
    public String createCategory(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";
        //TODO : Get hashmap
        return null;
    }

    @Delete
    public String removeCategory(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";
        try {
            ManagerAccountController.getInstance().removeCategory(getQueryValue("categoryName"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
