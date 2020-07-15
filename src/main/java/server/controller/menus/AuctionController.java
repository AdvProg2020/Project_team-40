package server.controller.menus;

import server.model.Auction;
import server.model.users.User;

import java.util.HashMap;

public class AuctionController {
    private static AuctionController auctionController = new AuctionController();

    private AuctionController() {
        checkDeadlines();
    }

    private void checkDeadlines() {
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    HashMap<String, Auction> auctions = Auction.getAuctions();
                    for(Auction auction : auctions.values()) {

                    }
                }
            }
        }.start();
    }

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
