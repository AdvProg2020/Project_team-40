package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.MenuException;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import server.controller.menus.ProductController;

import java.util.HashMap;

public class ProductAttributesResource extends ServerResource {
    @Get
    public String getProductAttributes() {
        HashMap<String, String> attributes;
        try {
            attributes = ProductController.getInstance().getProductAttributes(getQueryValue("productID"));
        } catch (MenuException e) {
            throw new ResourceException(403, e);
        }
        return new YaGson().toJson(attributes, new TypeToken<HashMap<String, String>>(){}.getType());
    }
}
