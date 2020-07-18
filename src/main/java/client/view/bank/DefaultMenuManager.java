package client.view.bank;

import client.controller.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DefaultMenuManager implements Initializable {
    public Label nameLabel;
    public Label accountIDLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = Client.getInstance();
        nameLabel.setText("Dear " + client.getFirstName());
        accountIDLabel.setText("Your account ID is: " + client.getBankAccount());
    }
}
