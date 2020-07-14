package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.ServerAuthenticator;
import server.controller.accounts.ManagerAccountController;

public class SupportResource extends ServerResource{
    @Post
    public void createSupport(){
        ManagerAccountController manager = ManagerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        manager.createSupportAccount(username, password, firstName, lastName, email, phone);
        ServerAuthenticator.getInstance().addToManagerVerifier(username, password);
    }
}
