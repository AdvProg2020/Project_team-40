package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

public class GetDiscountHandler extends ServerResource {
    @Get
    public DiscountCode getDiscount(){
        try {
            return ManagerAccountController.getInstance().getDiscount(getQueryValue("code"));
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
