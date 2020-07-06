package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.requests.Request;

public class GetRequestHandler extends ServerResource {
    @Get
    public Request getRequestObject(){
        try {
            return ManagerAccountController.getInstance().getRequest(getQueryValue("requestID"));
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
