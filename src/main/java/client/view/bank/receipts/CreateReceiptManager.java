package client.view.bank.receipts;

import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateReceiptManager implements Initializable {
    public RadioButton deposit;
    public RadioButton withdraw;
    public RadioButton move;
    public TextField sourceIDField;
    public TextField destinationIDField;
    public TextField moneyField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        toggleGroup.getToggles().add(deposit);
        toggleGroup.getToggles().add(withdraw);
        toggleGroup.getToggles().add(move);
    }

    public void deposit() {
        sourceIDField.setDisable(true);
        destinationIDField.setDisable(false);
    }

    public void withdraw() {
        sourceIDField.setDisable(false);
        destinationIDField.setDisable(true);
    }

    public void move() {
        sourceIDField.setDisable(false);
        destinationIDField.setDisable(false);
    }

    public void createReceipt() {
        //TODO:
    }
}
