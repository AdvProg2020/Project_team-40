package server.server_resources.seller_customer_common;

import com.gilecode.yagson.YaGson;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.Auction;
import server.model.users.Seller;
import server.model.users.User;

import java.util.ArrayList;

public class AuctionsResource extends ServerResource {
    @Get
    public String getAuctions() {
        ArrayList<Auction> auctions;
        if(getQueryValue("role").equals("seller")) {
            auctions = ((Seller) User.getUserByUsername(getQueryValue("username"))).getAuctions();
        } else {
            auctions = Auction.getOnGoingAuctions();
        }
        return new YaGson().toJson(auctions, new TypeToken<ArrayList<Auction>>(){}.getType());
    }
}
