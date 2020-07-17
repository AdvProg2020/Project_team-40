package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;

public class BalanceResource extends ServerResource {
    @Get
    public String getBalance() throws AuthorizationException {
        Double balance = CustomerAccountController.getInstance().getBalance(getQueryValue("username"));
        return new YaGson().toJson(balance, Double.class);
    }
}
