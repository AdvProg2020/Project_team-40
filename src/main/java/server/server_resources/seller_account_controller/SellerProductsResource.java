package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.Product;

import java.util.ArrayList;

public class SellerProductsResource extends ServerResource {
    @Get
    public String getProducts() throws AuthorizationException {
        ArrayList<Product> products = SellerAccountController.getInstance().getSellerProducts(getQueryValue("username"));
        return new YaGson().toJson(products, new TypeToken<ArrayList<Product>>(){}.getType());
    }
}
