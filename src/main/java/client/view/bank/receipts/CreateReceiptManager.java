package client.view.bank.receipts;

import client.view.ValidInput;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateReceiptManager implements Initializable {
    public RadioButton deposit;
    public RadioButton withdraw;
    public RadioButton move;
    public TextField sourceIDField;
    public TextField destinationIDField;
    public TextField descriptionField;
    public TextField moneyField;

    private final String BLANK_ERROR = "Fill this field!";
    public Label toggleGroupError;
    public Label sourceIDError;
    public Label destinationIDError;
    public Label amountError;
    public Label serverError;

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
        try {
            validateFieldsInput();
            HashMap<String, String> queries = new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateFieldsInput() throws Exception {
        if(moneyField.getText().isBlank()) {
            amountError.setText(BLANK_ERROR);
            throw new Exception();
        }
        if(!deposit.isSelected() && !withdraw.isSelected() && !move.isSelected()) {
            toggleGroupError.setText("Choose a type!");
            throw new Exception();
        }
        if(!ValidInput.INTEGER.getStringMatcher(moneyField.getText()).matches()) {
            amountError.setText("Enter a number!");
            throw new Exception();
        }
        validateFieldsInput(sourceIDField, sourceIDError);
        validateFieldsInput(destinationIDField, destinationIDError);

    }

    private void validateFieldsInput(TextField idField, Label idError) throws Exception {
        if(!idField.isDisable() && (idField.getText().isBlank() ||
                !ValidInput.INTEGER.getStringMatcher(idField.getText()).matches())) {
            idError.setText("Enter a number!");
            throw new Exception();
        }
    }
}
