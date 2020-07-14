package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.users.User;

import java.util.ArrayList;

public class ManagerUsersResource extends ServerResource {
    @Get
    public ArrayList<User> getAllUsers(){
        return ManagerAccountController.getInstance().getAllUsers();
    }
}
