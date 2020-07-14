package client.view.account_menus.customer_view.orders_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RateMenu extends MenuManager implements Initializable {
    public JFXButton submitButton;
    public Rating rateBar;
    private String productId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void handleRateProduct() {
        try {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("username", Client.getInstance().getUsername());
            queries.put("rate", Integer.toString((int)rateBar.getRating()));
            queries.put("productID", productId);
            RequestHandler.put("/accounts/customer_account_controller/product/", null, queries, true, null);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED)) {
                ((Stage) submitButton.getScene().getWindow()).close();
                logout();
            }
            else {
                e.printStackTrace();
            }
        }
        finally {
            ((Stage) submitButton.getScene().getWindow()).close();
        }
    }
}
