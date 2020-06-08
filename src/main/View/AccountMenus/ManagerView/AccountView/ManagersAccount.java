package View.AccountMenus.ManagerView.AccountView;

import View.AccountMenus.ManagerView.CategoryView.CategoryMenu;
import View.AccountMenus.ManagerView.DiscountView.DiscountsMenu;
import View.AccountMenus.ManagerView.ManageProductsView.ManageProductsMenu;
import View.AccountMenus.ManagerView.ManageUsersView.ManageUsersMenu;
import View.AccountMenus.ManagerView.RequestsVeiw.RequestMenu;
import View.AccountMenus.PeopleAccountMenu;
import View.Menu;

import java.util.HashMap;

public class ManagersAccount extends PeopleAccountMenu {

    public ManagersAccount(Menu parentMenu) {
        super("Manager Account", parentMenu);
        HashMap<Integer, Menu>submenus = new HashMap<>();
        submenus.put(1, getShowInfo());
        submenus.put(2, getEditInfo());
        submenus.put(3, new ManageUsersMenu(this));
        submenus.put(4, new ManageProductsMenu(this));
        submenus.put(5, new DiscountsMenu(this));
        submenus.put(6, new RequestMenu(this));
        submenus.put(7, new CategoryMenu(this));
        submenus.put(8, getShowInfo());
        submenus.put(9, getEditInfo());
        this.setSubMenus(submenus);
    }

}
