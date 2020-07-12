package client.view.bank;

import client.view.MenuManager;
import client.view.account_menus.ThisUser;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class BankLoginManager extends MenuManager {
    public TextField usernameField;
    public PasswordField passwordField;
    public Label errorMessage;

    public void login() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorMessage.setText("Fill the above fields!");
            return;
        }
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
                ThisUser.setLatestToken(response);
                goToBank();
                goToAccountsMenu();
            }
            socket.close();
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
        }
    }
}
