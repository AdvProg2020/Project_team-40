package view.account_menus.manager_view.manage_users_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.users.User;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenu extends MenuManager implements Initializable {

    public Label usernameLabel;
    public Label firstNameLabel;
    public Label lastNameLabel;
    public Label emailLabel;
    public Label phoneLabel;
    public JFXButton backButton;
    public AnchorPane pane;
    public Label roleLabel;
    private ManagerAccountController managerAccountController;
    private User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();

    }

    public void setUsernameLabel(String username) {
        usernameLabel.setText(username);
    }

    public void setFirstNameLabel(String firstName) {
        firstNameLabel.setText(firstName);
    }

    public void setLastNameLabel(String lastName) {
        lastNameLabel.setText(lastName);
    }

    public void setEmailLabel(String email) {
        emailLabel.setText(email);
    }

    public void setPhoneLabel(String phone) {
        phoneLabel.setText(phone);
    }

    public void setRoleLabel(String role){
        roleLabel.setText(role);
    }

    public void setBackButton(JFXButton backButton) {
        this.backButton = backButton;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void handleCloseWindow(ActionEvent event) {
        ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
    }
}
