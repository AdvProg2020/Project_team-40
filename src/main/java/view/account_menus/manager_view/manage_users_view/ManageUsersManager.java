package view.account_menus.manager_view.manage_users_view;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.users.User;
import view.MenuManager;

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


}
