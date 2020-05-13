package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.Menu;
import model.Product;

import java.util.HashMap;

public class ManageProduct extends Menu {
    SellerAccountController sellerAccountController;
    Product product;

    public ManageProduct(Menu parentMenu, Product product) {
        super("Manage Product Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProduct());
        submenus.put(2, getViewBuyers());
        submenus.put(3, getEditProduct());
        submenus.put(4, getAddProductsQuantity());
        submenus.put(5, getReduceProductsQuantity());
        this.setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
        this.product = product;
    }

    private Menu getViewProduct() {
        return null;
    }

    private Menu getViewBuyers() {
        return null;
    }

    private Menu getEditProduct() {
        return null;
    }

    private Menu getAddProductsQuantity() {
        return null;
    }

    private Menu getReduceProductsQuantity() {
        return null;
    }
}
