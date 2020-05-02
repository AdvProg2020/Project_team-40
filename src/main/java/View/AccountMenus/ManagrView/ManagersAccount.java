package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.AccountMenus.PeopleAccountMenu;
import View.Menu;

import java.util.HashMap;

public class ManagersAccount extends PeopleAccountMenu {
    private ManagerAccountController managerAccountController;

    public ManagersAccount(Menu parentMenu) {
        super("Manager Account", parentMenu);
        HashMap<Integer, Menu>submenus = new HashMap<>();
        submenus.put(1, new ManageUsersMenu(this));
        this.setSubMenus(submenus);
    }
    public void show(){
        System.out.println("List of users:");
        for (String userName : managerAccountController.getAllUserNames()) {
            System.out.println(userName);
        }
        super.show();
    }

    @Override
    public void getShowInfo() {

    }

    @Override
    public void getEditInfo() {

    }
}
