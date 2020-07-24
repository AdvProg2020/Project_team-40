package client.view.account_menus.customer_view.cart_view;

import client.view.MenuManager;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class PayBankManager extends MenuManager implements Initializable {
    private static double priceWithoutDiscount;
    private static double priceWithDiscount;
    private static String code;

    public Label priceLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(priceWithDiscount == -1)
            priceLabel.setText(String.valueOf(priceWithoutDiscount));
        else
            priceLabel.setText(String.valueOf(priceWithDiscount));
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

    public void pay() {

    }
}
