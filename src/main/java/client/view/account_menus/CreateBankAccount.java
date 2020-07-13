package client.view.account_menus;

import client.controller.Client;
import client.view.MenuManager;
import client.view.ValidInput;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.model.users.User;

import java.io.*;
import java.net.Socket;

public class CreateBankAccount extends MenuManager {
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField repeatPasswordField;
    public Button createBankAccount;

    public void createAccount() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank() || repeatPasswordField.getText().isBlank()) {
            errorMessage.setText("Fill all the above Fields!");
        } else {
            try {
                Socket socket = new Socket(IP, BANK_PORT);
                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                User user = Client.getInstance().getUser();
                outputStream.writeUTF("create_account " + user.getFirstName() + " " + user.getLastName() + " " +
                        usernameField.getText() + " " + passwordField.getText() + " " + repeatPasswordField.getText());
                outputStream.flush();
                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                handleResponse(inputStream.readUTF(), user);
                socket.close();
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        }
    }

    private void handleResponse(String response, User user) throws Exception {
        if(!ValidInput.INTEGER.getStringMatcher(response).matches())
            throw new Exception(response);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        repeatPasswordField.setDisable(true);
        errorMessage.setText("Your account number is: " + response);
        createBankAccount.setText("let's go to my account!");
        createBankAccount.setOnMouseClicked(e -> goToAccountsMenu());
        user.setBankAccount(Integer.parseInt(response));
    }
}