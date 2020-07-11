package server.server_resources.accounts;

import exceptions.AccountsException;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;
import server.controller.accounts.AccountController;

public class AccountResource extends ServerResource {

    @Put
    public String login(String username) throws AccountsException {
        String password = getQueryValue("password");
        return AccountController.getInstance().login(username, password);
    }


}
