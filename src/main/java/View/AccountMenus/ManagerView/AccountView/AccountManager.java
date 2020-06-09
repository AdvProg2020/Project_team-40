package View.AccountMenus.ManagerView.AccountView;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountManager implements Initializable {
    public JFXButton homeButton;
    public Pane innerPane;

    public void setInnerPane(Parent root){
        innerPane.getChildren().clear();
        innerPane.getChildren().add(root);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Main/PersonalInfo.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }


    public void goToHomeMenu(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("PersonalInfo.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
        setInnerPane(root);
    }
}
