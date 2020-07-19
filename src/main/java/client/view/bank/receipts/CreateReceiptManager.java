package client.view.bank.receipts;

import client.controller.Client;
import client.controller.RequestHandler;
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
        sourceIDField.setText("-1");
    }

    public void withdraw() {
        sourceIDField.setDisable(false);
        destinationIDField.setDisable(true);
        destinationIDField.setText("-1");
    }

    public void move() {
        sourceIDField.setDisable(false);
        destinationIDField.setDisable(false);
    }

    public void createReceipt() {
        try {
            validateFieldsInput();
            HashMap<String, String> queries = new HashMap<>();
            queries.put("receipt type", getType());
            queries.put("username", Client.getInstance().getUsername());
            queries.put("money", moneyField.getText());
            queries.put("source", sourceIDField.getText());
            queries.put("destination", destinationIDField.getText());
            queries.put("description", descriptionField.getText());
            String response = RequestHandler.get("/bank/create_receipt_resources/", queries, true,
                    String.class);
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getType() throws Exception {
        if(deposit.isSelected())
            return "deposit";
        if(withdraw.isSelected())
            return "withdraw";
        if(move.isSelected())
            return "move";
        toggleGroupError.setText("Choose a type!");
        throw new Exception();
    }

    private void validateFieldsInput() throws Exception {
        if(moneyField.getText().isBlank()) {
            amountError.setText(BLANK_ERROR);
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
