package server.server_resources.customer_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

public class PaymentResource extends ServerResource {
    @Put
    public Log purchase() throws  AuthorizationException {
        try {
            String username = getQueryValue("username");
            String address = getQueryValue("address");
            String code = getQueryValue("code");
            double priceAfterDiscount = Double.parseDouble(getQueryValue("priceAfterDiscount"));
            double priceWithoutDiscount = Double.parseDouble(getQueryValue("priceWithoutDiscount"));
            return CustomerAccountController.getInstance().makePayment(username, address,code, priceAfterDiscount, priceWithoutDiscount);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
