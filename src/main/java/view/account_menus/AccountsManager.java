package view.account_menus;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import view.MenuManager;

public class AccountsManager extends MenuManager {

    public void setSecondaryInnerPane(Parent root, Pane secondaryInnerPane){
        secondaryInnerPane.getChildren().clear();
        secondaryInnerPane.getChildren().add(root);
    }
}
