package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

public class SellerBalanceResource extends ServerResource {

    @Get
    public Double getBalance() throws AuthorizationException {
        return SellerAccountController.getInstance().getBalance(getQueryValue("username"));
    }
}
