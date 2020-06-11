package view.account_menus.manager_view.manage_users_view;

import com.jfoenix.controls.JFXButton;
import controller.accounts.ManagerAccountController;
import exceptions.AccountsException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserItem implements Initializable {
    public JFXButton deleteUserButton;
    public JFXButton viewUserButton;
    private ManagerAccountController managerAccountController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = ManagerAccountController.getInstance();
    }
    private void loadUsers(VBox vBoxItems) {
        vBoxItems.getChildren().clear();
        for (String userName : managerAccountController.getAllUserNames()) {
            try {
                User user = managerAccountController.getUser(userName);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/empty_layouts/user_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(user, hBox);
                vBoxItems.getChildren().add(item);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setLabelsContent(User user, HBox hBox) {
        Label usernameLabel =((Label) hBox.getChildren().get(0));
        Label roleLabel =((Label) hBox.getChildren().get(1));
        Label firsNameLabel =((Label) hBox.getChildren().get(2));
        Label lastNameLabel =((Label) hBox.getChildren().get(3));
        Label emailLabel =((Label) hBox.getChildren().get(4));
        Label phoneLabel =((Label) hBox.getChildren().get(5));
        usernameLabel.setText(user.getUsername());
        firsNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        roleLabel.setText(user.getRole());
        emailLabel.setText(user.getEmail());
        phoneLabel.setText(user.getPhoneNo());
    }

    private void setLabelsContent(UserMenu userMenu, User user) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userMenu.setUsernameLabel(user.getUsername());
                userMenu.setFirstNameLabel(user.getFirstName());
                userMenu.setLastNameLabel(user.getLastName());
                userMenu.setEmailLabel(user.getEmail());
                userMenu.setPhoneLabel(user.getPhoneNo());
                userMenu.setRoleLabel(user.getRole());
            }
        });
    }

    public void handleDeleteUser(ActionEvent event) {
        HBox item = (HBox)((deleteUserButton.getParent()).getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String username =((Label)item.getChildren().get(0)).getText();
        try {
            managerAccountController.deleteUser(username);
        } catch (AccountsException e) {
            System.err.println(e.getMessage());
        }
        Platform.runLater(() -> loadUsers(items));

    }

    public void handleViewUser(ActionEvent event) {
        HBox item = (HBox)((deleteUserButton.getParent()).getParent());
        String username =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/empty_layouts/user.fxml"));
        try {
            User user = managerAccountController.getUser(username);
            AnchorPane pane = loader.load();
            UserMenu userMenu = loader.getController();
            setLabelsContent(userMenu, user);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 520, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
