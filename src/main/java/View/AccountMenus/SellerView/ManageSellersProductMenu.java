package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.Menu;

import java.util.HashMap;

public class ManageSellersProductMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersProductMenu(Menu parentMenu) {
        super("Manage Seller's Product Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProducts());
        submenus.put(2, manageProduct());
        submenus.put(3, getAddProduct());
        submenus.put(4, getRemoveProduct());
        this.setSubMenus(submenus);
    }

    private Menu getViewProducts() {
        return null;
    }

    private Menu manageProduct() {
        return null;
    }

    private Menu getAddProduct(){
        return null;
    }

    private Menu getRemoveProduct() {
        return null;
    }
}
