package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class RemoveCategoryHandler extends ServerResource {
    @Get
    public String removeCategory(){
        try {
            ManagerAccountController.getInstance().removeCategory(getQueryValue("categoryName"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
