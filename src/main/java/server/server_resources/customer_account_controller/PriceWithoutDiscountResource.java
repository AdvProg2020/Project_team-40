package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class PriceWithoutDiscountResource extends ServerResource {
    @Put
    public void setPriceWithoutDiscount(String username) throws AuthorizationException {
        CustomerAccountController.getInstance().setPriceWithoutDiscount(username);
    }
}
