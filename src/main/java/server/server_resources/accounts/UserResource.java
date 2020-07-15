package server.server_resources.accounts;

import exceptions.AccountsException;
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
    public HashMap<String, String> getUser(){
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
            if (user instanceof Seller)
                response.put("company", ((Seller)user).getCompanyInfo());
            return response;
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }

    @Put
    public void editUser(String username)  {
        AccountController.getInstance().editUser(username, getQueryValue("field"), getQueryValue("newAmount"));
    }
}
