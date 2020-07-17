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
        queries.put("password", passwordField.getText());
        RequestHandler.post("/accounts/account/", usernameField.getText(), queries, true, null);
    }
    /*

        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("get_token " + usernameField.getText() + " " + passwordField.getText());
            outputStream.flush();
            String response = inputStream.readUTF();
            if(response.equals("invalid username or password")) {
                errorMessage.setText(response);
            } else {
                Client.getInstance().setLatestToken(response);
                goToBank();
                goToAccountsMenu();
            }
            socket.close();
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
        }
     */

    //TODO: Can't work with the main stage after going to the bank, fix it
}
