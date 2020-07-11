package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.SellerAccountController;
import server.model.log.Log;

import java.util.ArrayList;

public class SalesHistoryResource extends ServerResource {
    @Get
    public ArrayList<Log> getSalesHistory() throws AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        return SellerAccountController.getInstance().getSalesHistory();
    }
}
