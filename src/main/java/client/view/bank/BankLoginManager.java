package client.view.bank;

import client.controller.RequestHandler;
import client.view.MenuManager;
import client.controller.Client;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class BankLoginManager extends MenuManager {
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorMessage;

    public void login() {
        if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorMessage.setText("Fill the above fields!");
            return;
        }
        HashMap<String, String> queries = new HashMap<>();
        queries.put("bank password", passwordField.getText());
        queries.put("username", Client.getInstance().getUsername());
        String response = (String) RequestHandler.post("/bank/login/", usernameField.getText(), queries, true, String.class);
        if (response.equals("invalid username or password")) {
            errorMessage.setText(response);
        } else {
            goToBank();
            goToAccountsMenu();
        }
    }
}
