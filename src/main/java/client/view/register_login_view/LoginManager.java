package client.view.register_login_view;

import client.controller.Client;
import client.controller.LoginGuard;
import client.controller.RequestHandler;
import client.view.ChangeListener;
import client.view.MenuManager;
import com.gilecode.yagson.com.google.gson.reflect.TypeToken;
import exceptions.InvalidInputException;
import exceptions.TooMuchLoginAttemptsException;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginManager extends MenuManager implements Initializable {
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button login;
    public Button register;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTextFieldsListeners();

    }

    private void addTextFieldsListeners() {
        usernameField.textProperty().addListener(new ChangeListener(40, usernameField));
        passwordField.textProperty().addListener(new ChangeListener(40, passwordField));
    }

    public void login() throws IOException {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorMessage.setText("Fill the above fields!");
        } else {
            try {
                HashMap<String, String> queries = new HashMap<>();
                queries.put("password", passwordField.getText());
                LoginGuard.login(usernameField.getText(), queries);
//                RequestHandler.post("/accounts/account/", usernameField.getText(), queries, false, null);
                HashMap<String, String> userQueries = new HashMap<>();
                userQueries.put("username", usernameField.getText());
                HashMap<String, String> userParameters = RequestHandler.get("/accounts/user/",userQueries, false, new TypeToken<HashMap<String, String>>(){}.getType());
                assert userParameters != null;
                Client client = Client.getInstance();
                client.setParameters(userParameters);
                if((!Client.getInstance().getRole().equals("Support")) && (client.getBankAccount() == null || client.getBankAccount() < 0))
                    setMainInnerPane("/layouts/create_bank_account.fxml");
                else
                    goToAccountsMenu();
            }
            catch (InvalidInputException | TooMuchLoginAttemptsException e) {
                errorMessage.setText(e.getMessage());
            }
        }
    }

    public void register() {
        setMainInnerPane("/layouts/register_menu.fxml");
    }
}
