package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import View.Menu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import exceptions.AccountsException;
import model.Product;

import java.util.ArrayList;
import java.util.HashMap;

public class ManageSellersProductMenu extends Menu {
    SellerAccountController sellerAccountController;

    public ManageSellersProductMenu(Menu parentMenu) {
        super("Manage Seller's Products Menu", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewProducts());
        submenus.put(2, manageProduct());
        submenus.put(3, getAddProduct());
        submenus.put(4, getRemoveProduct());
        this.setSubMenus(submenus);
        sellerAccountController = SellerAccountController.getInstance();
    }

    private Menu getViewProducts() {
        return new Menu("Products", this) {
            @Override
            public void show() {
                ArrayList<String> productsIds = sellerAccountController.getSellerProductIDs();
                int productNumber = 0;
                for(String productId: productsIds) {
                    try {
                        System.out.println(productNumber + ". " +
                                sellerAccountController.getProductDetails(productId).getName() +
                                "ID: " + productId);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                    productNumber++;
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu manageProduct() {
        return new Menu("Manage Product Menu", this) {
            @Override
            public void show() {
                getViewProducts().show();
                System.out.println("Enter product's number:");
            }

            @Override
            public void execute() {
                ArrayList<String> productsIds = sellerAccountController.getSellerProductIDs();
                int productNumber = getNumberOfNextMenu(productsIds.size());
                try {
                    Product product = sellerAccountController.getProductDetails(productsIds.get(productNumber - 1));
                } catch (AccountsException e) {

                }
            }
        };
    }

    private Menu getAddProduct(){
        return null;
    }

    private Menu getRemoveProduct() {
        return null;
    }
}
