package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class RemoveProductHandler extends ServerResource {
    @Get
    public String removeProduct(){
        try {
            ManagerAccountController.getInstance().removeProduct(getQueryValue("productID"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
