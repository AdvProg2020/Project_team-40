package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.DiscountCode;

import java.util.ArrayList;

public class ManagerAllDiscountsResource extends ServerResource {
    @Get
    public String getAllDiscounts(){
        ArrayList<DiscountCode> discountCodes = ManagerAccountController.getInstance().getAllDiscountCodes();
        return new YaGson().toJson(discountCodes, new TypeToken<ArrayList<DiscountCode>>(){}.getType());
    }
}
