package view.main_menu;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import model.users.User;
import view.MenuManager;
import view.register_login_view.RegisterManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuManager extends MenuManager implements Initializable {
    public Pane InnerPane;
    public JFXButton startButton;
    public JFXButton accountButton;
    public  JFXButton productsButton;
    public  JFXButton backButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        mainInnerPane = InnerPane;
        if (User.doesManagerExist()){
            backButton.setDisable(false);
            productsButton.setDisable(false);
            accountButton.setDisable(false);
        }
        else {
            startButton.setVisible(true);
            MenuManager.setMainMenuAccountButton(accountButton);
            MenuManager.setMainMenuProductButton(productsButton);
            MenuManager.setMainMenuBackButton(backButton);
        }
    }


    public void handleStartProgram() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/register_menu.fxml"));
        try {
            Pane pane = loader.load();
            RegisterManager registerManager = loader.getController();
            registerManager.customerButton.setDisable(true);
            registerManager.sellerButton.setDisable(true);
            registerManager.managerButton.setSelected(true);
            mainInnerPane.getChildren().clear();
            mainInnerPane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
