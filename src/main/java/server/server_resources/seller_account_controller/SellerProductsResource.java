package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

import java.util.ArrayList;

public class SellerProductsResource extends ServerResource {
    @Get
    public ArrayList<String> getProductIds() throws AuthorizationException {
        return SellerAccountController.getInstance().getSellerProductIDs(getQueryValue("username"));
    }
}
