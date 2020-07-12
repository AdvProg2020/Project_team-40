package client.view.register_login_view;

import client.controller.Client;
import client.controller.RequestHandler;
import client.view.MenuManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.restlet.resource.ResourceException;
import server.controller.accounts.AccountController;
import server.model.users.User;

import java.io.IOException;
import java.util.HashMap;

public class LoginManager extends MenuManager {
    private AccountController accountController = AccountController.getInstance();
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button login;
    public Button register;

    public void login() throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorMessage.setText("Fill the above fields!");
        } else {
            try {
                HashMap<String, String> queries = new HashMap<>();
                queries.put("password", passwordField.getText());
                RequestHandler.post("/accounts/account/", usernameField.getText(), queries, false, null);

                HashMap<String, String> userQueries = new HashMap<>();
                userQueries.put("username", usernameField.getText());
                User user = (User)RequestHandler.get("/accounts/account/",userQueries, false, User.class);
                Client.getInstance().setUser(user);
                if(Client.getInstance().getUser().getBankAccount() == null)
                    setMainInnerPane("/layouts/create_bank_account.fxml");
                else
                    goToAccountsMenu();
            } catch (ResourceException e) {
                errorMessage.setText(RequestHandler.getClientResource().getResponseEntity().getText());
            }
        }
    }

    public void register() {
        setMainInnerPane("/layouts/register_menu.fxml");
    }
}
