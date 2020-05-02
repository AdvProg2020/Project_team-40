package View;

import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.ManagerAccountController;
import Controller.Accounts.SellerAccountController;
import View.ShopingMenus.ProductsAndOffsMenus.OffsMenu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;

import java.util.HashMap;

public class MainMenu extends Menu{
    SellerAccountController sellerAccountController;
    ManagerAccountController mangerAccountController;
    CustomerAccountController customerAccountController;

    public MainMenu( Menu parentMenu) {
        super("Main Menu", parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(2, new ProductsMenu(this));
        subMenus.put(3, new OffsMenu(this));
        this.setSubMenus(subMenus);
    }

    public Menu getRegisterOrLogin() {
        return new Menu("Register And Login", this) {
            @Override
            public void show() {
                System.out.println("1. Register\n" +
                        "2. Login\n" +
                        "3. Back");
            }

            @Override
            public void execute() {
                int chosenNumber = getNumberOfNextMenu();
                if(chosenNumber == 1) {
                    getRegister();
                } else if(chosenNumber == 2) {
                    getLogin();
                } else if(chosenNumber == 3) {
                    parentMenu.show();
                    parentMenu.execute();
                }
            }

            @Override
            public int getNumberOfNextMenu() {
                int chosenMenu;
                while(true) {
                    while (!ConsoleCommand.INTEGER.getStringMatcher(input = scanner.nextLine().trim()).matches()) {
                        System.out.println("Please write the number of one of the options.");
                    }
                    chosenMenu = Integer.parseInt(input);
                    if(chosenMenu <= 3) {
                        break;
                    } else {
                        System.out.println("Please write the number of one of the options.");
                    }
                }
                return chosenMenu;
            }
        };
    }

    public Menu getRegister() {
        return null;
    }

    public Menu getLogin() {
        return null;
    }
}
