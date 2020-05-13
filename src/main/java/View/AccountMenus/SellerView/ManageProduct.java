package View.AccountMenus.SellerView;

import View.Menu;

import java.util.HashMap;

public class ManageProduct extends Menu {

    public ManageProduct(Menu parentMenu) {
        super("Manage Product Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProduct());
        submenus.put(2, getViewBuyers());
        submenus.put(3, getEditProduct());
        submenus.put(4, getAddProductsQuantity());
        submenus.put(5, getReduceProductsQuantity());
        this.setSubMenus(submenus);
    }

    public Menu getViewProduct() {
        return null;
    }

    public Menu getViewBuyers() {
        return null;
    }

    public Menu getEditProduct() {
        return null;
    }

    public Menu getAddProductsQuantity() {
        return null;
    }

    public Menu getReduceProductsQuantity() {
        return null;
    }
}
