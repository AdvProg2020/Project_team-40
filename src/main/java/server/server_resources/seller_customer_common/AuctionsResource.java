package server.server_resources.seller_customer_common;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import server.model.Auction;

import java.util.ArrayList;

public class AuctionsResource extends ServerResource {
    @Get
    public String getAuctions() {
        ArrayList<Auction> auctions;
        if(getQueryValue("role").equals("Seller")) {

        }
        return null;
    }
}
