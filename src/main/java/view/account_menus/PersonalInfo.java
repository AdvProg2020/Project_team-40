package view.account_menus;

import controller.accounts.AccountController;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import model.users.User;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonalInfo extends AccountMenu implements Initializable {
    private User user = AccountController.getInstance().getThisUser();
    public Pane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
