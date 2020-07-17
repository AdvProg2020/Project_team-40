package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AuthorizationException;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.Category;

import java.util.HashMap;

public class SellerAllCategoriesResource extends ServerResource {

    @Get
    public String getAllCategories() throws AuthorizationException {
        HashMap<String, Category> categories = SellerAccountController.getInstance().getAllCategories();
        return new YaGson().toJson(categories, new TypeToken<HashMap<String, Category>>(){}.getType());
    }
}
