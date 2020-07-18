package client.view.bank;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.HashMap;

public class CreateBankAccount extends MenuManager {
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField repeatPasswordField;
    public Button createBankAccount;

    public void createAccount() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || repeatPasswordField.getText().isBlank()) {
            errorMessage.setText("Fill all the above Fields!");
            return;
        }
        System.out.println("hi");
        HashMap<String, String> queries = new HashMap<>();
        Client client = Client.getInstance();
        queries.put("first name", client.getFirstName());
        queries.put("last name", client.getLastName());
        queries.put("bank username", usernameField.getText());
        queries.put("bank password", passwordField.getText());
        queries.put("repeat password", repeatPasswordField.getText());
        queries.put("username", client.getUsername());
        String response = RequestHandler.get("/bank/register/", queries, true, String.class);
        goToAccount(response);
    }

    private void goToAccount(String response) {
        try {
            int accountNumber = Integer.parseInt(response);
            usernameField.setDisable(true);
            passwordField.setDisable(true);
            repeatPasswordField.setDisable(true);
            errorMessage.setText("Your account number is: " + response);
            createBankAccount.setText("let's go to my account!");
            createBankAccount.setOnMouseClicked(e -> goToAccountsMenu());
            Client.getInstance().setBankAccount(accountNumber);
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
        }
    }
}