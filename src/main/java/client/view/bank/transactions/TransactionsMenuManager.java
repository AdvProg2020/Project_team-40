package client.view.bank.transactions;

import client.controller.Client;
import client.controller.RequestHandler;
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
    }

    public void createReceipt() {
    }
}
