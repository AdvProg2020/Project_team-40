package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.users.User;

import java.util.ArrayList;

public class ManagerUsersResource extends ServerResource {
    @Get
    public String getAllUsers(){
        ArrayList<User> users = ManagerAccountController.getInstance().getAllUsers();
        return new YaGson().toJson(users, new TypeToken<ArrayList<User>>(){}.getType());
    }
}
