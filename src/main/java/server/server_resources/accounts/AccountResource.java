package server.server_resources.accounts;

import exceptions.AccountsException;
import org.restlet.resource.Delete;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;

public class AccountResource extends ServerResource {

    @Post
    public String login(String username) throws AccountsException {
        String password = getQueryValue("password");
        return AccountController.getInstance().login(username, password);
    }

    @Delete
    public void logout(){
        AccountController.getInstance().logout();
        //TODO: change this
    }


}
