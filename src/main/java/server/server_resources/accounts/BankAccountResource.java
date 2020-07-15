package server.server_resources.accounts;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.User;

public class BankAccountResource extends ServerResource {
    @Put
    public void setBankAccount(String username){
        User.getUserByUsername(username).setBankAccount(Integer.parseInt(getQueryValue("bankAccount")));
    }
}
