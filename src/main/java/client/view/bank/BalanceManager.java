package client.view.bank;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.ValidInput;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class BalanceManager implements Initializable {
    public Label balanceLabel;
    public Label titleLabel;
    //TODO: What I wanted to do with the titleLabel????

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HashMap<String, String> queries = new HashMap<>();
        queries.put("username", Client.getInstance().getUsername());
        String response = RequestHandler.get("/bank/balance/", queries, true, String.class);
        if(ValidInput.INTEGER.getStringMatcher(response).matches()) {
            balanceLabel.setText(response);
        } else {
            titleLabel.setText("");
            balanceLabel.setText("It has been a while since the last time you've logged in\n" +
                    "Please exit and reenter your bank account");
        }
    }
}
