package View.AccountMenus.ManagrView;

import View.AccountMenus.PeopleAccountMenu;
import View.Menu;

import java.util.HashMap;

public class ManagersAccount extends PeopleAccountMenu {

    public ManagersAccount(Menu parentMenu) {
        super("Manager Account", parentMenu);
        HashMap<Integer, Menu>submenus = new HashMap<>();
        submenus.put(1, new ManageUsersMenu(this));
        submenus.put(2, new ManageProductsMenu(this));
        submenus.put(3, new DiscountsMenu(this));
        submenus.put(4, new RequestMenu(this));
        submenus.put(5, new CategoryMenu(this));
        this.setSubMenus(submenus);
    }

}
