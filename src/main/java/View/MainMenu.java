package View;

import Controller.Accounts.AccountController;
import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.ManagerAccountController;
import Controller.Accounts.SellerAccountController;
import View.ShopingMenus.ProductsAndOffsMenus.OffsMenu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import exceptions.AccountsException;

import java.util.HashMap;

public class MainMenu extends Menu{
    SellerAccountController sellerAccountController;
    ManagerAccountController mangerAccountController;
    CustomerAccountController customerAccountController;

    public MainMenu( Menu parentMenu) {
        super("Main Menu", parentMenu);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        subMenus.put(1, new ProductsMenu(this));
        subMenus.put(2, new OffsMenu(this));
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
                    getRegister().show();
                    getRegister().execute();
                } else if(chosenNumber == 2) {
                    getLogin().show();
                    getLogin().execute();
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
        return new Menu("Register", this) {
            @Override
            public void show() {
                //TODO
            }

            @Override
            public void execute() {
                super.execute();
            }
        };
    }

    public Menu getLogin() {
        return new Menu("Login", this) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                while (true) {
                System.out.println("Enter your username:");
                String username = scanner.nextLine();
                System.out.println("Enter your password:");
                String password = scanner.nextLine();
                    try {
                        AccountController.getInstance().login(username, password);
                        parentMenu.show();
                        parentMenu.execute();
                    } catch (AccountsException e) {
                        e.printStackTrace();
                        //TODO: HANDLE EXCEPTION PROPERLY
                        System.out.println("Error");
                        continue;
                    }
                }
            }
        };
    }
}
