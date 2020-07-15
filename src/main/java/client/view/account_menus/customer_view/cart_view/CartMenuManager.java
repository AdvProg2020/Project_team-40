package client.view.account_menus.customer_view.cart_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;
import server.model.log.Log;
import server.model.users.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CartMenuManager extends MenuManager implements Initializable {
    public GridPane purchaseInformationPane;
    public AnchorPane mainPane;
    public Label emptyCartLabel;
    public Label priceLabel;
    public Label discountError;
    public Label addressError;
    public Label purchaseError;
    public TextField discountField;
    public TextArea addressField;
    public Button discountButton;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
        requestQueries.put("username", Client.getInstance().getUsername());
        HashMap<Product, Integer> cart = (HashMap) RequestHandler.get("/accounts/customer_account_controller/cart/", requestQueries, true, HashMap.class);
        assert cart != null;
        if (cart.isEmpty()) {
            mainPane.getChildren().remove(purchaseInformationPane);
            emptyCartLabel.setText("You have not chosen any product\nDo you want to see our offers?\nGo to products' Menu...");
        } else {
            Double totalPrice = (Double) RequestHandler.get("/accounts/customer_account_controller/cart_total_price/", requestQueries, true, Double.class);
            priceLabel.setText(Double.toString(totalPrice));
        }
    }

    public void applyDiscount() {
        if (discountField.getText().isBlank()) {
            discountError.setText("Enter a code!");
        } else {
            try {
                requestQueries.clear();
                requestQueries.put("username", Client.getInstance().getUsername());
                RequestHandler.put("/accounts/customer_account_controller/discount/", discountField.getText(), requestQueries, true, null);
                discountButton.setOnMouseClicked(e -> disableDiscountCode());
                discountButton.setText("disable discount code");
                discountField.setDisable(true);
                discountError.setText("");
                requestQueries.clear();
//                Double priceAfterDiscount =
//                priceLabel.setText(String.valueOf(priceAfterDiscount));
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        discountError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    private void disableDiscountCode() {
        try {
            requestQueries.clear();
            //TODO: Implement details
            discountField.setDisable(false);
            discountButton.setText("apply");
            double totalPrice = ((Customer)Client.getInstance().getUser()).getTotalPriceOfCart();
            priceLabel.setText(Double.toString(totalPrice));
            discountButton.setOnMouseClicked(e -> applyDiscount());
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    public void purchase() {
        if (addressField.getText().isBlank()) {
            addressError.setText("Write you address!");
        } else {
            requestQueries.clear();
            RequestHandler.put("/accounts/customer_account_controller/receiver_info/", addressField.getText(), requestQueries, true, null);
            try {
                requestQueries.clear();
                requestQueries.put("username", Client.getInstance().getUsername());
                //TODO: Complete follow
//                requestQueries.put("code", );
//                requestQueries.put("address", );
//                requestQueries.put("priceWithoutDiscount", );
//                requestQueries.put("priceAfterDiscount", );
                Log log = (Log) RequestHandler.put("/accounts/customer_account_controller/payment/", null, requestQueries, true, Log.class);
                LogMenuManager.setLog(log);
                setSecondaryInnerPane("/layouts/customer_menus/purchase_menus/log_design.fxml");
            } catch (ResourceException e) {
                if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                    logout();
                else {
                    try {
                        purchaseError.setText(RequestHandler.getClientResource().getResponseEntity().getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }
}
