package server.server_resources.manager_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

import java.util.Set;

public class ManagerAllCategoriesResource extends ServerResource {
    @Get
    public String getAllCats(){
        Set<String> cats = ManagerAccountController.getInstance().getAllCategories();
        return new YaGson().toJson(cats, Set.class);
    }
}
