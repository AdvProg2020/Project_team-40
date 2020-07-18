package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.AccountsException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.menus.AllProductsController;

import java.util.ArrayList;

public class AllSubCategoriesResource extends ServerResource {
    @Get
    public String getAllSubCats(){
        ArrayList<String> allCats = null;
        try {
            allCats = AllProductsController.getInstance().getAllSubCategories(getQueryValue("parentName"));
        } catch (AccountsException e) {
            throw new ResourceException(403, e);
        }
        return new YaGson().toJson(allCats, new TypeToken<ArrayList<String>>(){}.getType());
    }
}
