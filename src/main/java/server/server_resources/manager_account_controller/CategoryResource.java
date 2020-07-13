package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.enumerations.PropertyType;

import java.util.HashMap;

public class CategoryResource extends ServerResource {
    @Post
    public void createCategory(HashMap<String, PropertyType> properties) throws AccountsException {
        ManagerAccountController.getInstance().createCategory(getQueryValue("name"), getQueryValue("parentName"), properties);;
    }

    @Put
    public void editCategory(HashMap<String, String> toEdit) throws AccountsException {
        ManagerAccountController.getInstance().editCategory(getQueryValue("name"), toEdit);
    }

    @Delete
    public String removeCategory(){
        try {
            ManagerAccountController.getInstance().removeCategory(getQueryValue("categoryName"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
