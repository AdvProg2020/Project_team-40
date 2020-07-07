package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.ManagerAccountController;

public class AcceptRequestHandler extends ServerResource {
    @Get
    public String acceptRequest(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";
        try {
            ManagerAccountController.getInstance().acceptRequest(getQueryValue("requestID"));
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
