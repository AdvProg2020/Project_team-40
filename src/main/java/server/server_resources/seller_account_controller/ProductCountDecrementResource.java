package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

import javax.security.auth.login.AccountException;

public class ProductCountDecrementResource extends ServerResource {

    @Put
    public void decrease(String productID) throws  AuthorizationException {
        try {
            SellerAccountController.getInstance().decreaseProductCount(Integer.parseInt(getQueryValue("addedQuantity")), productID);
        } catch (AccountException e) {
            throw new ResourceException(403, e);
        }
    }
}
