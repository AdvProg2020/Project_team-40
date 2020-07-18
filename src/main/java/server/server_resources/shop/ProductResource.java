package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Product;

public class ProductResource extends ServerResource {
    @Get
    public String getProduct(){
        return new YaGson().toJson(Product.getProductById(getQueryValue("productID")), Product.class);
    }
}
