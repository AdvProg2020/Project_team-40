package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.AllProductsController;

import java.util.ArrayList;

public class AllCategoriesResource extends ServerResource {
    @Get
    public String getAllCategories (){
        ArrayList<String> allCats = AllProductsController.getInstance().getAllCategories();
        return new YaGson().toJson(allCats, new TypeToken<ArrayList<String>>(){}.getType());
    }
}
