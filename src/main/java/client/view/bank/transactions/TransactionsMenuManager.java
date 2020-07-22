package client.view.bank.transactions;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ValidInput;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class TransactionsMenuManager implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String response = RequestHandler.put("/bank/transactions/", Client.getInstance().getUsername(),
                new HashMap<>(), true, String.class);
        System.out.println(response);
        String[] transactions = response.split("/*");
        for(String transaction : transactions) {
            showTransactions(transaction);
        }
    }

    private void showTransactions(String transaction) {
        transaction = transaction.substring(1, transaction.length() - 2);
        System.out.println(transaction);
        String[] information = transaction.split(",");
        for(int i = 0; i < 6; i++) {
            setInformationInLabel(i, information[i]);
        }

    }

    private void setInformationInLabel(int index, String information) {
        String data = information.split(":")[1];
        if(ValidInput.INTEGER.getStringMatcher(data).matches()) {
            setItem(data);
        } else {
            setItem(data.substring(1, data.length() - 2));
        }
    }

    private void setItem(String data) {

    }

    public void createReceipt() {
    }
}
