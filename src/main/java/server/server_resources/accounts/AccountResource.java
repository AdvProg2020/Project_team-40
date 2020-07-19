package server.server_resources.accounts;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import org.restlet.resource.*;
import server.controller.accounts.AccountController;
import server.model.users.User;

public class AccountResource extends ServerResource {

    @Get
    public String doesUserExist(){
        boolean doesUserExist =  User.doesUserExist(getQueryValue("username"));
        return new YaGson().toJson(doesUserExist, boolean.class);
    }

    @Post
    public void login(String username)  {
        String password = getQueryValue("password");
        try {
            AccountController.getInstance().login(username, password);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Delete
    public void logout(){
        AccountController.getInstance().logout(getQueryValue("username"));
    }

}
