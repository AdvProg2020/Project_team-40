package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class CustomerProductResource extends ServerResource {
    @Put
    public void rateProduct() throws  AuthorizationException {
        try {
            CustomerAccountController.getInstance().rateProduct(getQueryValue("username"), getQueryValue("productID"),
                    Integer.parseInt(getQueryValue("rate")));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
