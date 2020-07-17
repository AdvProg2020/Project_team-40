package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;
import server.model.Product;

import java.util.ArrayList;

public class ManagerProductsResource extends ServerResource {
    @Get
    public String getAllProducts(){
        ArrayList<Product> products =  ManagerAccountController.getInstance().getAllProducts();
        return new YaGson().toJson(products, new TypeToken<ArrayList<Product>>(){}.getType());
    }
}
