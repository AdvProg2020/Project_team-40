package server.server_resources.bank;

import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.User;

public class BankRegisterResource extends ServerResource {

    @Get
    public String registerBankAccount() {
        return null;
    }

    /*
    @Put
    public void setBankAccount(String username){

        User.getUserByUsername(username).setBankAccount(Integer.parseInt(getQueryValue("bankAccount")));
    }

     */
}
