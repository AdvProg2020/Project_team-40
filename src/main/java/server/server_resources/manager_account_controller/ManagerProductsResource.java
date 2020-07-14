package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.Product;

import java.util.ArrayList;

public class ManagerProductsResource extends ServerResource {
    @Get
    public ArrayList<Product> getAllProducts(){
        return ManagerAccountController.getInstance().getAllProducts();
    }
}
