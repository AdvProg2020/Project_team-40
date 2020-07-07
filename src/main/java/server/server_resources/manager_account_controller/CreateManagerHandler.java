package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.ManagerAccountController;

public class CreateManagerHandler extends ServerResource {
    @Get
    public String createManager(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";
        ManagerAccountController manager = ManagerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        manager.createManagerAccount(username, password, firstName, lastName, email, phone);
        return "Successful";
    }
}
