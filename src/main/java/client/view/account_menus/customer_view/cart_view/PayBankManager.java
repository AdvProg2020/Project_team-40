package client.view.account_menus.customer_view.cart_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.restlet.resource.ResourceException;
import server.model.log.Log;
import server.model.requests.Request;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class PayBankManager extends MenuManager implements Initializable {
    private static double priceWithoutDiscount;
    private static double priceWithDiscount;
    private static String code;
    private static String address;

    public Label amountLabel;
    public TextField usernameField;
    public TextField passwordField;
    public Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(priceWithDiscount == -1)
            amountLabel.setText(String.valueOf(priceWithoutDiscount));
        else
            amountLabel.setText(String.valueOf(priceWithDiscount));
    }

    public static void setPriceWithoutDiscount(double priceWithoutDiscount) {
        PayBankManager.priceWithoutDiscount = priceWithoutDiscount;
    }

    public static void setPriceWithDiscount(double priceWithDiscount) {
        PayBankManager.priceWithDiscount = priceWithDiscount;
    }

    public static void setCode(String code) {
        PayBankManager.code = code;
    }

    public static void setAddress(String address) {
        PayBankManager.address = address;
    }

    public void pay() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorLabel.setText("Fill all the above fields!");
        } else {
            HashMap<String, String> queries = new HashMap<>();
            queries.put("bank username", usernameField.getText());
            queries.put("bank password", passwordField.getText());
            queries.put("amount", amountLabel.getText());
            queries.put("username", Client.getInstance().getUsername());
            queries.put("discount code", code);
            queries.put("address", address);
            queries.put("price without discount", String.valueOf(priceWithoutDiscount));
            try {
                Log log = RequestHandler.get("/accounts/customer_account_controller/pay_by_bank/", queries,
                        true, Log.class);
                LogMenuManager.setLog(log);
            } catch (ResourceException e) {
                errorLabel.setText(e.getMessage());
            }
        }
    }
}
