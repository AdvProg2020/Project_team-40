package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.CustomerAccountController;

public class CustomerProductResource extends ServerResource {
    @Put
    public void rateProduct() throws AccountsException, AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        CustomerAccountController.getInstance().rateProduct(getQueryValue("productID"), Integer.parseInt(getQueryValue("rate")));
    }
}
