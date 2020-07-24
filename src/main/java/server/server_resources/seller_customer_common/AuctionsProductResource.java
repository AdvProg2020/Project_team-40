package server.server_resources.seller_customer_common;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Auction;
import server.model.Product;

public class AuctionsProductResource extends ServerResource {
    @Get
    public String getAuctionsProduct() {
        Auction auction = Auction.getAuctionById(getQueryValue("auction ID"));
        return new YaGson().toJson(Product.getProductById(auction.getProductId()), Product.class);
    }
}
