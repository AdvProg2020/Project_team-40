package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class UserResource extends ServerResource {
    @Get
    public String deleteUser(){
        String username = getQueryValue("username");
        try {
            ManagerAccountController.getInstance().deleteUser(username);
        } catch (AccountsException e) {
            return e.getMessage();
        }
        return "Successful";
    }
}
