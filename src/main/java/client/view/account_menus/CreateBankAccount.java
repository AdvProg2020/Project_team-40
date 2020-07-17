package client.view.account_menus;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import client.view.ValidInput;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Queue;

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
        } else {
            HashMap<String, String> queries = new HashMap<>();
            Client client = Client.getInstance();
            queries.put("first name", client.getFirstName());
            queries.put("last name", client.getLastName());
            queries.put("bank username", usernameField.getText());
            queries.put("bank password", passwordField.getText());
            queries.put("repeat password", repeatPasswordField.getText());
            String response = RequestHandler.get("/accounts/seller_account_controller/company_info/", queries,
                    true, String.class);
        }
    }
    /*
    try {
                Socket socket = new Socket(IP, BANK_PORT);
                DataOutputStream outputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));;
                outputStream.writeUTF("create_account " + Client.getInstance().getFirstName() + " " +
                Client.getInstance().getLastName() + " " + usernameField.getText() + " " + passwordField.getText() + " "
                + repeatPasswordField.getText());
                outputStream.flush();
                DataInputStream inputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                handleResponse(inputStream.readUTF(), Client.getInstance().getUsername());
                socket.close();
            } catch (Exception e) {
                errorMessage.setText(e.getMessage());
            }

     */

    private void handleResponse(String response, String username) throws Exception {
        if(!ValidInput.INTEGER.getStringMatcher(response).matches())
            throw new Exception(response);
        usernameField.setDisable(true);
        passwordField.setDisable(true);
        repeatPasswordField.setDisable(true);
        errorMessage.setText("Your account number is: " + response);
        createBankAccount.setText("let's go to my account!");
        createBankAccount.setOnMouseClicked(e -> goToAccountsMenu());
        Client.getInstance().setBankAccount(Integer.parseInt(response));
        HashMap<String, String> queries = new HashMap<>();
        queries.put("bankAccount", response.toString());
        RequestHandler.put("/accounts/bank_account", username, queries, false, null);
    }
}