package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.Product;

import java.util.HashMap;

public class CartResource extends ServerResource {
    @Get
    public String getCart(){
        HashMap<Product, Integer> cart = CustomerAccountController.getInstance().getCart(getQueryValue("username"));
        return new YaGson().toJson(cart, new TypeToken<HashMap<String, Product>>(){}.getType());
    }
}
