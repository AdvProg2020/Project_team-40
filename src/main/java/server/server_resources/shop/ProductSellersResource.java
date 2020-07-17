package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.controller.menus.ProductController;

import java.util.ArrayList;

public class ProductSellersResource extends ServerResource {
    @Get
    public String getProductSellers(){
        ArrayList<String> sellers = ProductController.getInstance().getSellersForProduct(getQueryValue("productID"));
        return new YaGson().toJson(sellers, new TypeToken<ArrayList<String>>(){}.getType());
    }
}
