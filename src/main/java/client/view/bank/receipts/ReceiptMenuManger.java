package client.view.bank.receipts;

import client.controller.Client;
import client.controller.RequestHandler;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import server.model.Product;
import server.model.Receipt;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ReceiptMenuManger implements Initializable {
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<Integer, Receipt> receipts = RequestHandler.post("/bank/receipts/",
                Client.getInstance().getUsername(), new HashMap<>(), true,
                new TypeToken<HashMap<Integer, Receipt>>(){}.getType());
        for(Receipt receipt : receipts.values()) {
            createReceiptItem(receipt);
        }
        //TODO: Test createReceiptItem
        //TODO: Consider the case where token is expired...
    }

    private void createReceiptItem(Receipt receipt) {
        try {
            AnchorPane item = FXMLLoader.load(getClass().getResource("/layouts/bank_menus/receipts/receipt_item.fxml"));
            ((Label)item.getChildren().get(0)).setText(String.valueOf(receipt.getID()));
            ((Label)item.getChildren().get(1)).setText(String.valueOf(receipt.getOrigin()));
            ((Label)item.getChildren().get(2)).setText(String.valueOf(receipt.getDestination()));
            ((Label)item.getChildren().get(3)).setText(String.valueOf(receipt.getMoney()));
            ((Label)item.getChildren().get(4)).setText(String.valueOf(receipt.getDestination()));
            vBoxItems.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createReceipt() {

    }
}
