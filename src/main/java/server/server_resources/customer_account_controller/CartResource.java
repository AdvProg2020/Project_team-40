package server.server_resources.customer_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.Product;

import java.util.HashMap;

public class CartResource extends ServerResource {
    @Get
    public HashMap<Product, Integer> getCart(){
        return CustomerAccountController.getInstance().getCart(getQueryValue("username"));
    }
}
