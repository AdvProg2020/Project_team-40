package server.server_resources.accounts;

import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class UserResource extends ServerResource {
    @Get
    public User getUser() throws AccountsException, AuthorizationException {
        return AccountController.getInstance().getUser(getQueryValue("username"));
    }

    @Put
    public void editUser(String username) throws AuthorizationException {
        AccountController.getInstance().editUser(username, getQueryValue("field"), getQueryValue("newAmount"));
    }
}
