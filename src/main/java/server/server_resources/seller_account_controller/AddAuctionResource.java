package server.server_resources.seller_account_controller;

import com.gilecode.yagson.YaGson;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import server.controller.menus.ChatController;
import server.model.Auction;
import server.model.Product;
import server.model.chat.Chat;

import java.util.Date;

public class AddAuctionResource extends ServerResource {
    @Post
    public void addAuction(String entity) {
        Date deadline = new YaGson().fromJson(entity, Date.class);
        String productId = getQueryValue("product ID");
        new Auction(deadline, productId, (int) Product.getProductById(productId).getPrice(), getQueryValue("seller ID"),
                ChatController.getInstance().createChat("Auction for " + Product.getProductById(productId).getName()).getId());
    }
}
