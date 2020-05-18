package View.AccountMenus.SellerView;

import Controller.Accounts.SellerAccountController;
import Controller.Menus.AllProductsController;
import View.AccountMenus.PeopleAccountMenu;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.log.Log;
import model.users.Customer;
import model.users.Seller;

import java.util.ArrayList;
import java.util.HashMap;

public class SellerAccount extends PeopleAccountMenu {
    SellerAccountController sellerAccountController;

    public SellerAccount(Menu parentMenu) {
        super("Seller Account", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        sellerAccountController = SellerAccountController.getInstance();
        submenus.put(1, getShowInfo());
        submenus.put(2, getEditInfo());
        submenus.put(3, getCompanyInfo());
        submenus.put(4, getShowCategories());
        submenus.put(5, getViewBalance());
        submenus.put(6, getIncreaseCredit());
        if(sellerAccountController.getHasPermission()) {
            setSubMenusWithPermission(submenus);
        } else {
            this.setSubMenus(submenus);
        }
    }

    private void setSubMenusWithPermission(HashMap<Integer, Menu> submenus) {
        submenus.put(7, new ManageSellersProductsMenu(this));
        submenus.put(8, new ManageSellersOffsMenu(this));
        submenus.put(9, getSalesHistory());
        this.setSubMenus(submenus);
    }

    @Override
    public void show() {
        super.show();
        if(!sellerAccountController.getHasPermission()) {
            System.out.println("You need the Managers permission to launch you selling activity.");
        }
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
        return new Menu("Categories", this) {
            @Override
            public void show() {
                AllProductsController allProductsController = AllProductsController.getInstance();
                ArrayList<String> categories = allProductsController.getAllCategories();
                for(String category: categories) {
                    System.out.println(category);
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

    private Menu getIncreaseCredit() {
        return new Menu("Increase Credit", this) {
            @Override
            public void show() {
                getViewBalance().show();
                System.out.println("Enter the amount of money you want to add to your credit:");
            }

            @Override
            public void execute() {
                double money = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                        "Please enter a valid number."));
                Seller seller = (Seller) sellerAccountController.getThisUser();
                seller.setCredit(seller.getCredit() + money);
                System.out.println("Credit successfully increased.");
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
}
