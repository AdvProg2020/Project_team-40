package server.server_resources.customer_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.accounts.CustomerAccountController;
import server.model.Product;
import server.model.users.Customer;
import server.model.users.User;

import java.util.HashMap;

public class CartResource extends ServerResource {
    @Get
    public String getCart(){
        HashMap<Product, Integer> cart = CustomerAccountController.getInstance().getCart(getQueryValue("username"));
        return new YaGson().toJson(cart, new TypeToken<HashMap<String, Product>>(){}.getType());
    }

    @Post
    public void removeProductFromCart() {
        ((Customer) User.getUserByUsername(getQueryValue("username"))).getCart().
                remove(getQueryValue("product ID"));
    }
}
