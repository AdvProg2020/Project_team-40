package client.view.account_menus.customer_seller_common_view;

import client.controller.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;

import java.net.URL;
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
    public Label proposedPriceLabel;
    //proposedPriceLabel is the price proposed by this customer
    public Label auctionPriceLabel;
    public Label errorLabel;

    public Button proposeHigherPrice;
    public Spinner priceSpinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commonLoad();
        if(Client.getInstance().getRole().equalsIgnoreCase("Seller")) {
            loadForSeller();
        } else {
            loadForCustomer();
        }
    }

    private void loadForCustomer() {

    }

    private void loadForSeller() {

    }

    private void commonLoad() {

    }
}
