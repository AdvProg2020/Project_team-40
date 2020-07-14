package server.server_resources.seller_account_controller;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

import java.util.ArrayList;

public class OffResource extends ServerResource {

    @Put
    public void editOff(String offID) throws  AuthorizationException {
        try {
            SellerAccountController.getInstance().editOff(getQueryValue("username"), offID, getQueryValue("field"), getQueryValue("newField"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }
    }

    @Post
    public void createOff(ArrayList<String> productIDs) throws  AuthorizationException {
        String start = getQueryValue("startDate");
        String end = getQueryValue("endDate");
        double percentage = Double.parseDouble(getQueryValue("percentage"));
        try {
            SellerAccountController.getInstance().addOffToSeller(getQueryValue("username"), productIDs, start, end, percentage);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);

        }

    }
}
