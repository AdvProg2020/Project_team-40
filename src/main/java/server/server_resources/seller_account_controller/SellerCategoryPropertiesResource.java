package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.accounts.SellerAccountController;
import server.model.enumerations.PropertyType;

import java.util.HashMap;

public class SellerCategoryPropertiesResource extends ServerResource {
    @Get
    public String getProperties(){
        HashMap<String, PropertyType> properties = SellerAccountController.getInstance().getCategoryProperties(getQueryValue("category"));
        return new YaGson().toJson(properties, new TypeToken<HashMap<String, PropertyType>>(){}.getType());
    }
}
