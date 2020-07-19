package server.server_resources.accounts;

import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AccountsException;
import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.AccountController;
import server.model.users.Seller;
import server.model.users.User;

import java.util.HashMap;

public class UserResource extends ServerResource {
    @Get
    public String getUser(){
        YaGson mapper = new YaGson();
        HashMap<String, String> response = new HashMap<>();
        try {
            User user =  AccountController.getInstance().getUser(getQueryValue("username"));
            response.put("username", user.getUsername());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("password", user.getPassword());
            response.put("email", user.getEmail());
            response.put("phoneNumber", user.getPhoneNo());
            response.put("role", user.getRole());
            response.put("bankAccount", Integer.toString(user.getBankAccount()));
            response.put("isOnline", Boolean.toString(user.isOnline()));
            if (user instanceof Seller)
                response.put("company", ((Seller)user).getCompanyInfo());
            return mapper.toJson(response, new TypeToken<HashMap<String, String>>(){}.getType());
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Put
    public void editUser(String username)  {
        AccountController.getInstance().editUser(username, getQueryValue("field"), getQueryValue("newAmount"));
    }
}
