package server.server_resources.seller_account_controller;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.enumerations.PropertyType;

import java.util.HashMap;

public class SellerCategoryPropertiesResource extends ServerResource {
    @Get
    public HashMap<String, PropertyType> getProperties(){
        return SellerAccountController.getInstance().getCategoryProperties(getQueryValue("category"));
    }
}
