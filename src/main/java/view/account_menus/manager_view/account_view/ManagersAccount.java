package view.account_menus.manager_view.account_view;

import view.account_menus.manager_view.category_view.CategoryMenu;
import view.account_menus.manager_view.discount_view.DiscountsMenu;
import view.account_menus.manager_view.manage_products_view.ManageProductsMenu;
import view.account_menus.manager_view.manage_users_view.ManageUsersMenu;
import view.account_menus.manager_view.requests_view.RequestMenu;
import view.account_menus.PeopleAccountMenu;
import view.Menu;

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
