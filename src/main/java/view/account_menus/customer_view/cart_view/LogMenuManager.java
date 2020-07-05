package view.account_menus.customer_view.cart_view;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.log.Log;
import view.MenuManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LogMenuManager extends MenuManager implements Initializable {
    private static Log log;
    public VBox logBox;

    public static void setLog(Log log) {
        LogMenuManager.log = log;
    }

    public void returnToAccount() {
        setSecondaryInnerPane("/layouts/accounts_menu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] information = log.toString().split("\n");
        for(String line : information) {
            Label label = new Label(line);
            label.setFont(new Font(20));
            logBox.getChildren().add(label);
        }
    }
}
