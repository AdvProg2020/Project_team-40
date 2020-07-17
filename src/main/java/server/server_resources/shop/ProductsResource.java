package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Product;

import java.util.ArrayList;

public class ProductsResource extends ServerResource {
    @Get
    public String getAllProducts(){
        ArrayList<Product> products = new ArrayList<>(Product.getAllProducts().values());
        return new YaGson().toJson(products, new TypeToken<ArrayList<Product>>(){}.getType());
    }
}
