package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.AccountMenus.PeopleAccountMenu;
import View.Menu;

import java.util.HashMap;

public class SellerAccount extends PeopleAccountMenu {
    SellerAccountController sellerAccountController = SellerAccountController.getInstance();

    public SellerAccount(Menu parentMenu) {
        super("Seller Account", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowInfo());
        submenus.put(2, getEditInfo());
        submenus.put(3, new ManageSellersProductMenu(this));
        submenus.put(4, new ManageSellersOffsMenu(this));
        submenus.put(5, getCompanyInfo());
        submenus.put(6, getSalesHistory());
        submenus.put(7, getShowCategories());
        submenus.put(8, getViewBalance());
        this.setSubMenus(submenus);
    }

    public Menu getCompanyInfo(){
        return null;
    }

    public Menu getSalesHistory(){
        return null;
    }


    public Menu getRemove(){
        return null;
    }

    public Menu getShowCategories(){
        return null;
    }

    public Menu getViewBalance(){
        return null;
    }
}
