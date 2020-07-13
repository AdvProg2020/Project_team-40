package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class DeclineRequestResource extends ServerResource {
    @Put
    public void acceptRequest() throws AccountsException {
           ManagerAccountController.getInstance().declineRequest(getQueryValue("requestID"));
    }
}
