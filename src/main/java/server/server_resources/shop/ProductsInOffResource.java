package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Off;
import server.model.Product;

import java.util.ArrayList;

public class ProductsInOffResource extends ServerResource {
    @Get
    public String getProductsInOff(){
        ArrayList<Product> productsInOff = new ArrayList<>();
        for (Off off : Off.getAllOffs().values()) {
            productsInOff.addAll(off.getProducts());
        }
        return new YaGson().toJson(productsInOff, new TypeToken<ArrayList<Product>>(){}.getType());
    }
}
