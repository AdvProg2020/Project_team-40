package view.account_menus.customer_view.cart_view;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import model.log.Log;
import view.account_menus.AllAccountsManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LogMenuManager extends AllAccountsManager implements Initializable {
    private static Log log;
    private static Parent lastMenuRoot;
    public Label logLabel;

    public static void setLog(Log log) {
        LogMenuManager.log = log;
    }

    public static void setLastMenuRoot(Parent lastMenuRoot) {
        LogMenuManager.lastMenuRoot = lastMenuRoot;
    }

    public void returnToAccount() {
        setSecondaryInnerPane(lastMenuRoot);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logLabel.setText(log.toString());
    }
}
