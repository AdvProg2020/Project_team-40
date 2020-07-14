package client.view.account_menus.manager_view.manage_users_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import client.view.register_login_view.RegisterManager;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.users.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ManageUsersManager extends MenuManager implements Initializable {

    public VBox vBoxItems;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadUsers();
    }

    private void loadUsers() {
        try{
        ArrayList<?> users = (ArrayList<?>) RequestHandler.get("/accounts/manager_account_controller/users/", new HashMap<>(), true, ArrayList.class);
        assert users != null;
        for (Object obj : users) {
            try {
                User user = (User) obj;
                AnchorPane item = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/manager_menus/manager_users_menus/user_item.fxml"));
                HBox hBox = (HBox) item.getChildren().get(0);
                setLabelsContent(user, hBox);
                vBoxItems.getChildren().add(item);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
        catch (ResourceException e){
        if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
            logout();
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


    public void handleAddManager() {

        try {
            RegisterManager.setIsByManager(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/register_menu.fxml"));
            Pane pane = loader.load();
            RegisterManager registerManager = loader.getController();
            registerManager.registerLabel.setText("New Manager");
            registerManager.sellerButton.setDisable(true);
            registerManager.customerButton.setDisable(true);
            registerManager.managerButton.setSelected(true);
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 1100, 610));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddSupport() {

        try {
            RegisterManager.setIsByManager(true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/register_menu.fxml"));
            Pane pane = loader.load();
            RegisterManager registerManager = loader.getController();
            registerManager.registerLabel.setText("New Support");
//            registerManager.sellerButton.setDisable(true);
//            registerManager.customerButton.setDisable(true);
//            registerManager.managerButton.setSelected(true);
            Stage stage = new Stage();
            stage.setScene(new Scene(pane, 1100, 610));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
