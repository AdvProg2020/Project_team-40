package server.server_resources.shop;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Product;

public class ProductSellerResource extends ServerResource {
    @Get
    public String getProductBySeller(){
        Product product = Product.getProductWithSellerAndName(getQueryValue("name"), getQueryValue("sellerName"));
        return new YaGson().toJson(product, Product.class);
    }
}
