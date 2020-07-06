package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class RemoveDiscountHandler extends ServerResource {
    @Get
    public String removeDiscount(){
        try {
            ManagerAccountController.getInstance().removeDiscount(getQueryValue("code"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
