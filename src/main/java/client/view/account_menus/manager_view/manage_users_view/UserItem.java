package client.view.account_menus.manager_view.manage_users_view;

import client.controller.RequestHandler;
import client.view.MenuManager;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.restlet.data.Status;
import org.restlet.resource.ResourceException;
import server.model.users.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UserItem extends MenuManager implements Initializable {
    public JFXButton deleteUserButton;
    public JFXButton viewUserButton;
    private HashMap<String, String> requestQueries;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        requestQueries = new HashMap<>();
    }
    private void loadUsers(VBox vBoxItems) {
        try {
            vBoxItems.getChildren().clear();
            ArrayList<?> users = (ArrayList<?>) RequestHandler.get("/accounts/manager_account_controller/users/", requestQueries, true, ArrayList.class);
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

    private void setLabelsContent(UserMenu userMenu, HashMap<?, ?> userParams) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                userMenu.setUsernameLabel((String) userParams.get("username"));
                userMenu.setFirstNameLabel((String) userParams.get("firstName"));
                userMenu.setLastNameLabel((String) userParams.get("lastName"));
                userMenu.setEmailLabel((String) userParams.get("email"));
                userMenu.setPhoneLabel((String) userParams.get("phoneNumber"));
                userMenu.setRoleLabel((String) userParams.get("role"));
            }
        });
    }

    public void handleDeleteUser() {
        HBox item = (HBox)((deleteUserButton.getParent()).getParent());
        VBox items =(VBox)(item.getParent()).getParent();
        String username =((Label)item.getChildren().get(0)).getText();
        try {
            requestQueries.clear();
            requestQueries.put("username", username);
            RequestHandler.delete("/accounts/manager_account_controller/user/", requestQueries, true, null);
        } catch (ResourceException e) {
            if (e.getStatus().equals(Status.CLIENT_ERROR_UNAUTHORIZED))
                logout();
        }
        Platform.runLater(() -> loadUsers(items));

    }

    public void handleViewUser() {
        HBox item = (HBox)((deleteUserButton.getParent()).getParent());
        String username =((Label)item.getChildren().get(0)).getText();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/manager_menus/manager_users_menus/user.fxml"));
        try {
            requestQueries.clear();
            requestQueries.put("username", username);
            HashMap<?, ?> userParameters = (HashMap<?, ?>) RequestHandler.get("/accounts/user/",requestQueries, false, HashMap.class);
            AnchorPane pane = loader.load();
            UserMenu userMenu = loader.getController();
            setLabelsContent(userMenu, userParameters);
            Stage userWindow = new Stage();
            userWindow.setScene(new Scene(pane, 520, 600));
            userWindow.initModality(Modality.APPLICATION_MODAL);
            userWindow.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
