package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

import java.util.HashMap;

public class OrdersResource extends ServerResource {
    @Get
    public String getOrders() throws AuthorizationException {
        YaGson mapper = new YaGson();
        HashMap<String, Log> orders =  CustomerAccountController.getInstance().getOrders(getQueryValue("username"));
        return mapper.toJson(orders, new TypeToken<HashMap<String, Log>>(){}.getType());
    }
}
