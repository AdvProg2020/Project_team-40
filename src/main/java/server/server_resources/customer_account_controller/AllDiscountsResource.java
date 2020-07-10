package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.CustomerAccountController;
import server.model.DiscountCode;

import java.util.HashMap;

public class AllDiscountsResource extends ServerResource {
    @Get
    public HashMap<String, DiscountCode> getAllDiscounts() throws AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        return CustomerAccountController.getInstance().getDiscountCodes();
    }
}
