package server.controller.menus;

import server.model.Auction;
import server.model.users.User;

import java.util.ArrayList;
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
                ArrayList<Auction> passedAuctions = new ArrayList<>();
                while(true) {
                    ArrayList<Auction> auctions = Auction.getOnGoingAuctions();
                    passedAuctions.clear();
                    for(Auction auction : auctions) {
                        if(auction.hasReachedDeadline())
                            passedAuctions.add(auction);
                    }
                    for(Auction auction : passedAuctions) {
                        auction.finish();
                    }
                    try {
                        sleep(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
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
