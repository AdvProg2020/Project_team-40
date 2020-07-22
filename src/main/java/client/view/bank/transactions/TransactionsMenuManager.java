package client.view.bank.transactions;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ValidInput;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TransactionsMenuManager implements Initializable {
    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String response = RequestHandler.put("/bank/transactions/", Client.getInstance().getUsername(),
                new HashMap<>(), true, String.class);
        if(response.isBlank())
            return;
        String[] transactions = response.split("\\*");
        for(String transaction : transactions) {
            showTransactions(transaction);
        }
    }

    private void showTransactions(String transaction) {
        //TODO: the line below: Caused by: java.lang.StringIndexOutOfBoundsException: begin 1, end -2, length 0
        transaction = transaction.substring(1, transaction.length() - 1);
        String[] information = transaction.split(",");
        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getResource("/layouts/bank_menus/transactions/transaction.fxml"));
            HBox item = (HBox) pane.getChildren().get(0);
            for(int i = 0; i < 6; i++) {
                setInformationInLabel(5 - i, information[i], item);
            }
            vBoxItems.getChildren().add(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setInformationInLabel(int index, String information, HBox item) {
        System.out.println("information = " + information);
        String data = information.split(":")[1];
        System.out.println("data = " + data);
        if(!ValidInput.INTEGER.getStringMatcher(data).matches()) {
            data = data.substring(1, data.length() - 1);
        }
        ((Label) item.getChildren().get(index)).setText(data);
    }
}
