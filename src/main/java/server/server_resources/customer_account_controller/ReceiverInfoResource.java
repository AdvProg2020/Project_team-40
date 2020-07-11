package server.server_resources.customer_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.CustomerAccountController;

public class ReceiverInfoResource extends ServerResource {
    @Put
    public void setReceiverInfo(String info) throws AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        CustomerAccountController.getInstance().setReceiverInfo(info);
    }
}
