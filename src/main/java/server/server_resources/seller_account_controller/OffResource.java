package server.server_resources.seller_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.SellerAccountController;

import java.util.ArrayList;

public class OffResource extends ServerResource {

    @Put
    public void editOff(String offID) throws AccountsException, AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        SellerAccountController.getInstance().editOff(offID, getQueryValue("field"), getQueryValue("newField"));
    }

    @Post
    public void createOff(ArrayList<String> productIDs) throws AccountsException, AuthorizationException {
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            throw new AuthorizationException("Authentication failed.");
        String start = getQueryValue("startDate");
        String end = getQueryValue("endDate");
        double percentage = Double.parseDouble(getQueryValue("percentage"));
        SellerAccountController.getInstance().addOffToSeller(productIDs, start, end, percentage);

    }
}
