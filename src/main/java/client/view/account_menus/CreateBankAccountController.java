package client.view.account_menus;

import client.view.MenuManager;
import client.view.ValidInput;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.model.users.User;

import java.io.*;
import java.net.Socket;

public class CreateBankAccountController extends MenuManager {
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public PasswordField repeatPasswordField;

    public void createAccount() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank() || repeatPasswordField.getText().isBlank()) {
            errorMessage.setText("Fill all the above Fields!");
        } else {
            try {
                Socket socket = new Socket("127.0.0.1", 8080);
                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                User user = User.getUserByUsername(ThisUser.getUsername());
                outputStream.writeUTF("create_account " + user.getFirstName() + " " + user.getLastName() + " " +
                        usernameField.getText() + " " + passwordField.getText() + " " + repeatPasswordField.getText());
                outputStream.flush();
                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                handleResponse(inputStream.readUTF());
                socket.close();
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }
        }
    }

    private void handleResponse(String response) throws Exception {
        if(!ValidInput.INTEGER.getStringMatcher(response).matches())
            throw new Exception(response);

    }
}
