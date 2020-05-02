package View;

import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.ManagerAccountController;
import Controller.Accounts.SellerAccountController;

import java.util.HashMap;

public class MainMenu extends Menu{
    SellerAccountController sellerAccountController;
    ManagerAccountController mangerAccountController;
    CustomerAccountController customerAccountController;

    public MainMenu( Menu parentMenu) {
        super("Main Menu", parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        this.setSubMenus(subMenus);
    }

    public Menu getRegisterOrLogin() {
        return null;
    }

    public Menu getRegister() {
        return null;
    }

    public Menu getLogin() {
        return null;
    }
}
