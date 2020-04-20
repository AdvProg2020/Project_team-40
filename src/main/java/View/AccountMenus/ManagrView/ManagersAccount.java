package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.Menu;

public class ManagersAccount extends Menu {
    ManagerAccountController managerAccountController;

    public ManagersAccount(Menu parentMenu) {
        super("Manager Account", parentMenu);
    }
}
