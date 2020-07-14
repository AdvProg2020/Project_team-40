package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.*;
import server.controller.accounts.ManagerAccountController;
import server.model.enumerations.PropertyType;

import java.util.HashMap;

public class CategoryResource extends ServerResource {
    @Post
    public void createCategory(HashMap<String, PropertyType> properties)  {
        try {
            ManagerAccountController.getInstance().createCategory(getQueryValue("name"), getQueryValue("parentName"), properties);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
        ;
    }

    @Put
    public void editCategory(HashMap<String, String> toEdit)  {
        try {
            ManagerAccountController.getInstance().editCategory(getQueryValue("name"), toEdit);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void removeCategory(){
        try {
            ManagerAccountController.getInstance().removeCategory(getQueryValue("categoryName"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }

    }
}
