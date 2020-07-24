package server.server_resources.seller_customer_common;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Auction;

public class GetAuctionResource extends ServerResource {
    @Get
    public String getAuction() {
        return new YaGson().toJson(Auction.getAuctionById(getQueryValue("auction ID")), Auction.class);
    }
}
