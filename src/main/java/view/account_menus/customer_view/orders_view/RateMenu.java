package view.account_menus.customer_view.orders_view;

import com.jfoenix.controls.JFXButton;
import server.controller.accounts.CustomerAccountController;
import exceptions.AccountsException;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.util.ResourceBundle;

public class RateMenu implements Initializable {
    public JFXButton submitButton;
    public Rating rateBar;
    private CustomerAccountController customerAccountController;
    private String productId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerAccountController = CustomerAccountController.getInstance();
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void handleRateProduct() {
        try {
            customerAccountController.rateProduct(productId, (int)rateBar.getRating());
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        finally {
            ((Stage) submitButton.getScene().getWindow()).close();
        }
    }
}
