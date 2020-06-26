package view.account_menus.manager_view.manage_users_view;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.users.User;
import view.MenuManager;
import view.register_login_view.RegisterManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsersManager extends MenuManager implements Initializable {

    public VBox vBoxItems;
    private controller.accounts.ManagerAccountController managerAccountController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        managerAccountController = controller.accounts.ManagerAccountController.getInstance();
        loadUsers();
    }

    private void loadUsers() {
        for (String userName : managerAccountController.getAllUserNames()) {
            try {
                User user = managerAccountController.getUser(userName);
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_users_menus/user_item.fxml"));
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


    public void handleAddManager(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/register_menu.fxml"));
        RegisterManager registerManager = loader.getController();
        registerManager.registerLabel.setText("New Manager");
        registerManager.sellerButton.setDisable(true);
        registerManager.customerButton.setDisable(true);
        registerManager.managerButton.setSelected(true);
        try {
            Pane pane = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 1100, 610));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
