package View;

import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.ManagerAccountController;
import Controller.Accounts.SellerAccountController;

public class MainMenu extends Menu{
    SellerAccountController sellerAccountController;
    ManagerAccountController mangerAccountController;
    CustomerAccountController customerAccountController;

    public MainMenu( Menu parentMenu) {
        super("Main Menu", parentMenu);
    }

    public Menu getRegister() {
        return null;
    }

    public Menu getLogin() {
        return null;
    }
}
