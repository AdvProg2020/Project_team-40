package server.server_resources.accounts;

import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class UserResource extends ServerResource {
    @Get
    public User getUser(){
        try {
            return AccountController.getInstance().getUser(getQueryValue("username"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Put
    public void editUser(String username)  {
        AccountController.getInstance().editUser(username, getQueryValue("field"), getQueryValue("newAmount"));
    }
}
