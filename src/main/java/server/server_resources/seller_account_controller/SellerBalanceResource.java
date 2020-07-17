package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;

public class SellerBalanceResource extends ServerResource {

    @Get
    public String getBalance() throws AuthorizationException {
        Double balance = SellerAccountController.getInstance().getBalance(getQueryValue("username"));
        return new YaGson().toJson(balance, Double.class);
    }
}
