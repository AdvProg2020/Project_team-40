package server.server_resources.accounts;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class AccountResource extends ServerResource {

    @Post
    public void login(String username) throws AccountsException {
        String password = getQueryValue("password");
        AccountController.getInstance().login(username, password);
    }

    @Get
    public boolean doesUserExist(){
        return User.doesUserExist(getQueryValue("username"));
    }

}
