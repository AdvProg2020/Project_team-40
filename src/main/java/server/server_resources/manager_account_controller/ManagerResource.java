package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.ServerAuthenticator;
import server.controller.accounts.ManagerAccountController;
import server.model.users.User;

public class ManagerResource extends ServerResource {
    @Post
    public void createManager(){
        ManagerAccountController manager = ManagerAccountController.getInstance();
        String username = getQueryValue("username");
        String password = getQueryValue("password");
        String firstName = getQueryValue("firstName");
        String lastName = getQueryValue("lastName");
        String email = getQueryValue("email");
        String phone = getQueryValue("phoneNumber");
        manager.createManagerAccount(username, password, firstName, lastName, email, phone);
        ServerAuthenticator.getInstance().addToManagerVerifier(username, password);
    }

    @Get
    public String doesManagerExist(){
        boolean doesManagerExist =  User.doesManagerExist();
        return new YaGson().toJson(doesManagerExist, boolean.class);
    }
}
