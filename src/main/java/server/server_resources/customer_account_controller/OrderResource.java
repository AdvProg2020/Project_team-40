package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import exceptions.AccountsException;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.log.Log;

public class OrderResource extends ServerResource {
    @Get
    public String getOrder() throws  AuthorizationException {
        YaGson mapper = new YaGson();
        try {
            Log log = CustomerAccountController.getInstance().getOrder(getQueryValue("orderID"), getQueryValue("username"));
            return mapper.toJson(log, Log.class);
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
    }
}
