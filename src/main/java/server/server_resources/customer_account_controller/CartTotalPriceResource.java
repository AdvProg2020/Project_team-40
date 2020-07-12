package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class CartTotalPriceResource extends ServerResource {
    @Get
    public double getTotalPrice() throws AuthorizationException {
        return CustomerAccountController.getInstance().getCartTotalPrice(getQueryValue("username"));
    }
}
