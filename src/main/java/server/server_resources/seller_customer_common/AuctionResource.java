package server.server_resources.seller_customer_common;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.Auction;

public class AuctionResource extends ServerResource {
    @Get
    public String getAuction() {
        return new YaGson().toJson(Auction.getAuctionById(getQueryValue("auction ID")), Auction.class);
    }

    @Put
    public String proposePrice() {
        Auction auction = Auction.getAuctionById(getQueryValue("auction ID"));
        auction.setHighestPrice(Integer.parseInt(getQueryValue("proposed price")));
        auction.setHighestPriceCustomer(getQueryValue("username"));
        return new YaGson().toJson("successful", String.class);
    }
}
