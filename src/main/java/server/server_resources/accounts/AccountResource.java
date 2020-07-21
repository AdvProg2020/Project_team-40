package server.server_resources.accounts;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import exceptions.BlockedIPException;
import org.restlet.Request;
import org.restlet.resource.*;
import server.ServerAuthenticator;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class AccountResource extends ServerResource {

    @Get
    public String doesUserExist(){
        boolean doesUserExist =  User.doesUserExist(getQueryValue("username"));
        return new YaGson().toJson(doesUserExist, boolean.class);
    }

    @Put
    public void blockIpAddress(){
        ServerAuthenticator.getInstance().addToBlockedIPs(Request.getCurrent().getClientInfo().getAddress());
    }

    @Post
    public void login(String username)  {
        try {
            ServerAuthenticator.getInstance().checkIP(Request.getCurrent().getClientInfo().getAddress());
            String password = getQueryValue("password");
            AccountController.getInstance().login(username, password);
        } catch (AccountsException | BlockedIPException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void logout(){
        AccountController.getInstance().logout(getQueryValue("username"));
    }

}
