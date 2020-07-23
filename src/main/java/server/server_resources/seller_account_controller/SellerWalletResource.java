package server.server_resources.seller_account_controller;

import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import server.model.users.Seller;
import server.model.users.User;

public class SellerWalletResource extends ServerResource {
    @Put
    public void increaseWalletCredit(String username){
        double amount = Double.parseDouble(getQueryValue("amount"));
        Seller seller = (Seller) User.getUserByUsername(username);
        seller.changeWalletCredit(amount);
    }
}
