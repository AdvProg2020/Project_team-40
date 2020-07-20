package client.view.bank.receipts;

import client.controller.Client;
import client.controller.RequestHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import server.model.Receipt;
import server.model.requests.Request;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReceiptItemManager {
    public Label idLabel;

    public void payReceipt() {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("receipt ID", idLabel.getText());
        String response = RequestHandler.get("/bank/receipts/", queries, true, String.class);
    }
}
