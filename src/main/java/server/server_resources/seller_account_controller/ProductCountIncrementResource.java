package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

public class ProductCountIncrementResource extends ServerResource {

    @Put
    public void increase(String productID) throws AuthorizationException {
        SellerAccountController.getInstance().increaseProductsCount(Integer.parseInt(getQueryValue("addedQuantity")), productID);
    }
}
