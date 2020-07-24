package client.view.account_menus.customer_view.cart_view;


import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.HashMap;

public class CartItemManager extends MenuManager {
    public Label productIDLabel;

    public void removeProduct() {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        queries.put("product ID", productIDLabel.getText());
        RequestHandler.post("/accounts/customer_account_controller/cart/", new String(), queries, true,
                null);
        ((VBox) productIDLabel.getParent().getParent().getParent()).getChildren().remove(productIDLabel.getParent().getParent());
    }
}
