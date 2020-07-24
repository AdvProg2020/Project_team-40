package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import client.controller.RequestHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import server.model.Auction;
import server.model.Product;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class AuctionManager implements Initializable {
    //Product's information:
    public GridPane productPane;
    public Label productNameLabel;
    public Label productIdLabel;
    public Label defaultPriceLabel;
    public Label categoryLabel;
    public Label companyLabel;
    public Label sellerLabel;
    public Label ratingLabel;

    //Auction's information:
    public GridPane auctionPane;
    public Label auctionIDLabel;
    public Label auctionDeadlineLabel;
    public Label statusLabel;
    //statusLabel tells if auction has ended to seller and its text will be "Price you've proposed" to customer
    public Label winnerLabel;
    //proposedPriceLabel is the price proposed by this customer
    public Label auctionPriceLabel;
    public Label errorLabel;

    public Button proposeHigherPrice;
    public Spinner priceSpinner;

    private Auction auction;
    private Product product;
    private static String auctionID;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("auction ID", auctionID);
        auction = RequestHandler.get("/accounts/seller_customer_common/auction/", queries, true,
                Auction.class);
        product = RequestHandler.get("/accounts/seller_customer_common/auctions_product/", queries, true,
                Product.class);
        commonLoad();
        if(Client.getInstance().getRole().equalsIgnoreCase("Seller")) {
            loadForSeller();
        } else {
            loadForCustomer();
        }
    }

    private void loadForCustomer() {
        priceSpinner.getValueFactory().setValue(auction.getHighestPrice());
        statusLabel.setText("Winner till now:");
        winnerLabel.setText(auction.getHighestPriceCustomer());
    }

    private void loadForSeller() {
        auctionPane.getChildren().remove(priceSpinner);
        auctionPane.getChildren().remove(proposeHigherPrice);

        if(auction.hasReachedDeadline())
            statusLabel.setText("Auction has ended!");
        else
            statusLabel.setText("Auction continues!");
    }

    private void commonLoad() {
        auctionIDLabel.setText(auction.getId());
        auctionDeadlineLabel.setText(auction.getDeadline().toString());
        defaultPriceLabel.setText(String.valueOf(product.getPrice()));
        auctionPriceLabel.setText(String.valueOf(auction.getHighestPrice()));

        categoryLabel.setText(product.getCategory());
        companyLabel.setText(product.getCompany());
        productIdLabel.setText(product.getProductId());
        sellerLabel.setText(product.getSellerUsername());
        productNameLabel.setText(product.getName());
    }

    public static void setAuctionID(String auctionID) {
        AuctionManager.auctionID = auctionID;
    }

    public void proposePrice() {

    }
}
