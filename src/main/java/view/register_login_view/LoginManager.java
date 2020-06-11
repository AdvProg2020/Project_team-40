package view.register_login_view;

import controller.accounts.AccountController;
import exceptions.AccountsException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import view.MenuManager;

public class LoginManager extends MenuManager {
    private AccountController accountController = AccountController.getInstance();
    public Label errorMessage;
    public TextField usernameField;
    public PasswordField passwordField;
    public Button login;
    public Button register;

    public void login() {
        if(usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
            errorMessage.setText("Fill the above fields!");
        } else {
            try {
                accountController.login(usernameField.getText(), passwordField.getText());
                goToAccountsMenu();
            } catch (AccountsException e) {
                errorMessage.setText(e.getMessage());
            }
        }
    }

    public void register() {
        setInnerPane("/layouts/register_menu.fxml");
    }
}
