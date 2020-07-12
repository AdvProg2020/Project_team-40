package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class PriceAfterDiscountResource extends ServerResource {
    @Get
    public Double getPriceAfterDiscount() throws AuthorizationException {
        return CustomerAccountController.getInstance().getPriceAfterDiscount();
    }
}
