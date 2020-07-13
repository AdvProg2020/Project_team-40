package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.requests.Request;

public class RequestResource extends ServerResource {
    @Get
    public Request getRequestObject() throws AccountsException {

        return ManagerAccountController.getInstance().getRequest(getQueryValue("requestID"));

    }
}
