package server.controller.menus;

import server.model.Auction;
import server.model.users.User;

public class AuctionController {
    private static AuctionController auctionController = new AuctionController();

    private AuctionController() {}

    public AuctionController getInstance() {
        return auctionController;
    }

    public void setHigherPriceForAuction(String auctionID, String username, int price) {
        Auction auction = Auction.getAuctionById(auctionID);
        auction.setHighestPrice(price);
        auction.setHighestPriceCustomer(User.getUserByUsername(username).getUsername());
    }

    public boolean hasReachedDeadline(String auctionID) {
        return Auction.getAuctionById(auctionID).hasReachedDeadline();
    }
}
