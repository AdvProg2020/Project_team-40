package server.server_resources.manager_account_controller;

import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

public class ManagerUserResource extends ServerResource {
    @Delete
    public void deleteUser(){
        String username = getQueryValue("username");
        try {
            ManagerAccountController.getInstance().deleteUser(username);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
