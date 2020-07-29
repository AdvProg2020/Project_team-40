package server.controller.menus;

import server.model.Auction;

import java.util.ArrayList;

public class AuctionController {
    private static AuctionController auctionController = new AuctionController();

    private AuctionController() {}

    public static void checkDeadlines() {
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
}
