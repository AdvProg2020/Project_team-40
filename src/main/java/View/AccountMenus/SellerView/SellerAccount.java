package View.AccountMenus.SellerView;

import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.SellerAccountController;
import Controller.Menus.AllProductsController;
import View.AccountMenus.PeopleAccountMenu;
import View.Menu;
import exceptions.AccountsException;
import model.log.Log;

import java.util.ArrayList;
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

    private Menu getCompanyInfo(){
        return new Menu("Company Info", this) {
            @Override
            public void show() {
                System.out.println(sellerAccountController.getCompanyInfo());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getSalesHistory(){
        return new Menu("Sales History", this) {
            @Override
            public void show() {
                ArrayList<Log> logs = sellerAccountController.getSalesHistory();
                for(Log log: logs) {
                    System.out.println(log);
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getShowCategories(){
        return new Menu("Categories:", this) {
            @Override
            public void show() {
                AllProductsController allProductsController = AllProductsController.getInstance();
                ArrayList<String> categories = allProductsController.getAllCategories();
                for(String category: categories) {
                    System.out.println(category + ":\n");
                    try {
                        ArrayList<String> subCategories = allProductsController.getAllSubCategories(category);
                        for(String subCategory: subCategories) {
                            System.out.println("\t" + subCategory);
                        }
                    } catch (AccountsException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getViewBalance(){
        return new Menu("Balance", this) {
            @Override
            public void show() {
                System.out.println("Balance: " + sellerAccountController.getBalance());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
}
