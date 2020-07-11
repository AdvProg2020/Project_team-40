package server.server_resources.manager_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.ManagerAccountController;

import java.util.Set;

public class ManagerAllCategoriesResource extends ServerResource {
    @Get
    public Set<String> getAllCats(){
        return ManagerAccountController.getInstance().getAllCategories();
    }
}
