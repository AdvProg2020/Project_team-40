package view.account_menus;

import view.MenuManager;

public abstract class AccountMenu extends MenuManager {
    public void home() {
        setInnerPane("/layouts/user_info.fxml");
    }
}
