package client.view.account_menus.customer_view.cart_view;

import server.controller.accounts.CustomerAccountController;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import server.model.log.Log;
import client.view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CartMenuManager extends MenuManager implements Initializable {
    private CustomerAccountController customerAccountController;
    public GridPane purchaseInformationPane;
    public AnchorPane mainPane;
    public Label emptyCartLabel;
    public Label priceLabel;
    public Label discountError;
    public Label addressError;
    public Label purchaseError;
    public TextField discountField;
    public TextField addressField;
    public Button discountButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();
        if(customerAccountController.getCart().isEmpty()) {
            mainPane.getChildren().remove(purchaseInformationPane);
            emptyCartLabel.setText("You have not chosen any product\nDo you want to see our offers?\nGo to products' Menu...");
        } else {
            priceLabel.setText(Double.toString(customerAccountController.getCartTotalPrice()));
        }
    }

    public void applyDiscount() {
        if(discountField.getText().isBlank()) {
            discountError.setText("Enter a code!");
        } else {
            try {
                customerAccountController.enterDiscountCode(discountField.getText());
                discountButton.setOnMouseClicked(e -> disableDiscountCode());
                discountButton.setText("disable discount code");
                discountField.setDisable(true);
                discountError.setText("");
                priceLabel.setText(String.valueOf(customerAccountController.getPriceAfterDiscount()));
            } catch (AccountsException e) {
                discountError.setText(e.getMessage());
            }
        }
    }

    private void disableDiscountCode() {
        customerAccountController.setPriceWithoutDiscount();
        discountField.setDisable(false);
        discountButton.setText("apply");
        priceLabel.setText(String.valueOf(customerAccountController.getCartTotalPrice()));
        discountButton.setOnMouseClicked(e -> applyDiscount());
    }

    public void purchase() {
        if(addressField.getText().isBlank()) {
            addressError.setText("Write you address!");
        } else {
            customerAccountController.setReceiverInfo(addressField.getText());
            try {
                Log log = customerAccountController.makePayment();
                LogMenuManager.setLog(log);
                setSecondaryInnerPane("/layouts/customer_menus/log_design.fxml");
            } catch (AccountsException e) {
                purchaseError.setText(e.getMessage());
            }
        }
    }
}
