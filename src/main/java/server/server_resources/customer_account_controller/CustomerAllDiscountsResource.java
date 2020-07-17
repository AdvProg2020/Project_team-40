package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.engine.header.DispositionWriter;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.DiscountCode;

import java.util.HashMap;

public class CustomerAllDiscountsResource extends ServerResource {
    @Get
    public String getAllDiscounts() throws AuthorizationException {
        HashMap<String, DiscountCode> allDiscounts =  CustomerAccountController.getInstance().getDiscountCodes(getAttribute("username"));
        return new YaGson().toJson(allDiscounts, new TypeToken<HashMap<String, DispositionWriter>>(){}.getType());
    }
}
