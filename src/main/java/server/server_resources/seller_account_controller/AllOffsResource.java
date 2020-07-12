package server.server_resources.seller_account_controller;

import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.Off;

import java.util.HashMap;

public class AllOffsResource extends ServerResource {

    @Get
    public HashMap<String, Off> getAllOffs() throws AuthorizationException {
        return SellerAccountController.getInstance().getAllOffs(getQueryValue("username"));
    }
}
