package client.view.bank.receipts;

import client.controller.Client;
import client.controller.RequestHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
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
        Label errorLabel = (Label)
                ((Pane)idLabel.getParent().getParent().getParent().getParent().getParent().getParent()).getChildren().get(4);
        if(response.equals("done successfully")) {
            VBox receiptsBox = (VBox) idLabel.getParent().getParent().getParent();
            receiptsBox.getChildren().remove(idLabel.getParent().getParent());
            errorLabel.setText("");
        } else {
            errorLabel.setText(response);
        }
        //TODO: Figure out why receipts are not removed after paying
    }
}
