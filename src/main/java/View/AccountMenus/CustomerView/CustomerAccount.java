package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.AccountMenus.PeopleAccountMenu;
import View.Menu;

import java.util.HashMap;

public class CustomerAccount extends PeopleAccountMenu {
    CustomerAccountController customerAccountController;

    public CustomerAccount(Menu parentMenu) {
        super("Customer Account", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new CartMenu(this));
        submenus.put(2, new OrdersMenu(this));
        submenus.put(3, getShowInfo());
        submenus.put(4, getEditInfo());
        submenus.put(5, getViewOrders());
        submenus.put(6, getViewBalance());
        submenus.put(7, getViewDiscountCodes());
        this.setSubMenus(submenus);
    }

    public Menu getViewOrders(){
        return null;
    }

    public Menu getViewBalance(){
        return null;
    }

    public Menu getViewDiscountCodes(){
        return null;
    }
}
