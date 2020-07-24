package client.view.account_menus.customer_view.cart_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.Product;
import server.model.log.Log;

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
    public VBox vBoxItems;
    private HashMap<String, String> requestQueries;

    private double priceWithoutDiscount;
    private double priceWithDiscount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        validateInputs();
        priceWithDiscount = -1;
        requestQueries = new HashMap<>();
        requestQueries.put("username", Client.getInstance().getUsername());
        HashMap<Product, Integer> cart =  RequestHandler.get("/accounts/customer_account_controller/cart/",
                requestQueries, true, new TypeToken<HashMap<Product, Integer>>(){}.getType());
        assert cart != null;
        if (cart.isEmpty()) {
            mainPane.getChildren().remove(purchaseInformationPane);
            emptyCartLabel.setVisible(true);
            emptyCartLabel.setText("You have not chosen any products, do you want to see our offers?\n" +
                    "Go to products' Menu...");
        } else {
            emptyCartLabel.setVisible(false);
            Double totalPrice = RequestHandler.get("/accounts/customer_account_controller/cart_total_price/",
                    requestQueries, true, Double.class);
            priceLabel.setText(Double.toString(totalPrice));
            priceWithoutDiscount = totalPrice;
            for(Product product : cart.keySet()) {
                load(product, cart.get(product));
            }
        }
    }

    private void load(Product product, Integer quantity) {
        try {
            AnchorPane item = FXMLLoader.load(getClass()
                    .getResource("/layouts/customer_menus/purchase_menus/cart_product_item.fxml"));
            HBox hBox = (HBox) item.getChildren().get(0);
            ((Label) hBox.getChildren().get(0)).setText(product.getProductId());
            ((Label) hBox.getChildren().get(1)).setText(String.valueOf(product.getPrice()));
            ((Label) hBox.getChildren().get(2)).setText(String.valueOf(quantity));
            vBoxItems.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateInputs() {
        if (discountField != null && !discountField.isDisable())
            discountField.textProperty().addListener(new ChangeListener(20, discountField));
    }

    public void applyDiscount() {
        if (discountField.getText().isBlank()) {
            discountError.setText("Enter a code!");
        } else {
            try {
                requestQueries.clear();
                requestQueries.put("username", Client.getInstance().getUsername());
                priceWithDiscount = RequestHandler.put("/accounts/customer_account_controller/discount/",
                        discountField.getText(), requestQueries, true, Double.class);
                discountButton.setOnMouseClicked(e -> disableDiscountCode());
                discountButton.setText("disable discount code");
                discountField.setDisable(true);
                discountError.setText("");
                requestQueries.clear();
                priceLabel.setText(String.valueOf(priceWithDiscount));
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
            requestQueries.clear();
            requestQueries.put("username", Client.getInstance().getUsername());

            priceLabel.setText(Double.toString(priceWithoutDiscount));
            priceWithDiscount = -1;
            discountButton.setOnMouseClicked(e -> applyDiscount());
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
    }

    public void payByWallet() {
        if (addressField.getText().isBlank()) {
            addressError.setText("Write you address!");
        } else {
            requestQueries.clear();
            RequestHandler.put("/accounts/customer_account_controller/receiver_info/", addressField.getText(), requestQueries, true, null);
            try {
                requestQueries.clear();
                requestQueries.put("username", Client.getInstance().getUsername());
                requestQueries.put("code", getDiscountCode());
                requestQueries.put("address", addressField.getText());
                requestQueries.put("priceWithoutDiscount", String.valueOf(priceWithoutDiscount));
                requestQueries.put("priceAfterDiscount", String.valueOf(priceWithDiscount));
                Log log =  RequestHandler.put("/accounts/customer_account_controller/payment/", null,
                        requestQueries, true, Log.class);
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

    private String getDiscountCode() {
        if(discountField.isDisable())
            return discountField.getText();
        else
            return null;
    }

    public void payThroughBank() {
        if(discountField.isDisable())
            PayBankManager.setCode(discountField.getText());
        PayBankManager.setPriceWithDiscount(priceWithDiscount);
        PayBankManager.setPriceWithoutDiscount(priceWithoutDiscount);
        setSecondaryInnerPane("/layouts/customer_menus/purchase_menus/pay_through_bank.fxml");
    }
}
