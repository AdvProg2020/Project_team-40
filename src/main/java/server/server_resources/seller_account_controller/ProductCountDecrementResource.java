package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.SellerAccountController;

import javax.security.auth.login.AccountException;

public class ProductCountDecrementResource extends ServerResource {

    @Put
    public void decrease(String productID) throws AccountException, AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        SellerAccountController.getInstance().decreaseProductCount(Integer.parseInt(getQueryValue("addedQuantity")), productID);
    }
}
