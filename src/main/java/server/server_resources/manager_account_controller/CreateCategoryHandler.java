package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.AuthenticationTokenHandler;

public class CreateCategoryHandler extends ServerResource {
    @Get
    public String createCategory(){
        if (!AuthenticationTokenHandler.authorize(getQueryValue("auth-token")))
            return "Authentication failed.";
        //TODO : Get hashmap
        return null;
    }
}
