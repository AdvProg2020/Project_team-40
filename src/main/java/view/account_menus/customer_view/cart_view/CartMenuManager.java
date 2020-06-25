package view.account_menus.customer_view.cart_view;

import controller.accounts.CustomerAccountController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class CartMenuManager extends MenuManager implements Initializable {
    private CustomerAccountController customerAccountController;
    public GridPane purchaseInformationPane;
    public AnchorPane mainPane;
    public Label emptyCartLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();
        if(customerAccountController.getCart().isEmpty()) {
            emptyCartError();
        } else {

        }

    }

    private void emptyCartError() {
        mainPane.getChildren().remove(purchaseInformationPane);
        emptyCartLabel.setText("You have not chose any product!");
    }
    //TODO: After payment, the previous page must be shown, implement it!


}
