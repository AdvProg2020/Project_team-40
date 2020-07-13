package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class AcceptRequestResource extends ServerResource {
    @Put
    public void acceptRequest(String requestId) throws AccountsException {
        ManagerAccountController.getInstance().acceptRequest(getQueryValue("requestID"));

    }
}
