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
    public Label errormessage;

    public void login() {
        try {
            Socket socket = new Socket(IP, BANK_PORT);
            DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            outputStream.writeUTF("Get_token " + usernameField.getText() + " " + passwordField.getText());
            outputStream.flush();
            String response = inputStream.readUTF();
            if(response.equals("invalid username or password")) {
                errormessage.setText(response);
            } else {
                ThisUser.setLatestToken(response);
                goToBank();
            }
        } catch (Exception e) {
            errormessage.setText(e.getMessage());
        }
    }
}
