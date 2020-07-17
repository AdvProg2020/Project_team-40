package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.log.Log;

import java.util.ArrayList;

public class SalesHistoryResource extends ServerResource {
    @Get
    public String getSalesHistory() throws AuthorizationException {
        ArrayList<Log> salesHistory =  SellerAccountController.getInstance().getSalesHistory(getQueryValue("username"));
        return new YaGson().toJson(salesHistory, new TypeToken<ArrayList<Log>>(){}.getType());
    }
}
