package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class CustomerDiscountResource extends ServerResource {
    @Put
    public void enterDiscount(String code) throws  AuthorizationException {
        try {
            CustomerAccountController.getInstance().checkDiscountCode(code, getQueryValue("username"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }
    }

}
