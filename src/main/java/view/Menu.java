package view;

import controller.accounts.AccountController;
import view.account_menus.custromer_view.account_view.CustomerAccount;
//import view.account_menus.manager_view.account_view.ManagersAccount;
import view.account_menus.PeopleAccountMenu;
import view.account_menus.seller_view.accounts_view.SellerAccount;
import view.main_menu.MainMenu;
import view.shopping_menus.products_and_offs_menus.offs_view.OffsMenu;
import view.shopping_menus.products_and_offs_menus.products_view.ProductsMenu;
import exceptions.DataException;
import javafx.stage.Stage;
import model.Loader;
import model.users.Customer;
import model.users.Seller;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    protected static Stage stage;
    protected String name;
    protected String input;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> subMenus;
    protected int numberOfOptions;
    //numberOfOptions tell the total number of next options including submenus, login and logout, back and exit etc.
    public static Scanner scanner;
    protected static ArrayList<Menu> allMenus;

    public Menu(String name, Menu parentMenu) {
        this.name = name;
        this.parentMenu = parentMenu;
    }

    public static void setScanner(Scanner scanner) {
        Menu.scanner = scanner;
    }

    public static void setStage(Stage stage) {
        Menu.stage = stage;
    }

    public String getName() {
        return name;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void setSubMenus(HashMap<Integer, Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public void show() {
        System.out.println(this.name + ":");
        numberOfOptions = subMenus.size() + 1;
        for(Integer menuNumber: subMenus.keySet()) {
            System.out.println(menuNumber + ". " + subMenus.get(menuNumber).getName());
        }
        if(!(this instanceof PeopleAccountMenu) && AccountController.getInstance().isLogin()) {
            System.out.println(numberOfOptions + ". My Account");
            numberOfOptions++;
        }
        if(!(this instanceof ProductsMenu)) {
            System.out.println(numberOfOptions + ". Products Menu");
            numberOfOptions++;
        }
        if(!(this instanceof OffsMenu)) {
            System.out.println(numberOfOptions + ". Off Menu");
            numberOfOptions++;
        }
        if(AccountController.getInstance().isLogin())
            System.out.println(numberOfOptions + ". Logout");
        else
            System.out.println(numberOfOptions + ". Register or Login");
        numberOfOptions++;
        if(parentMenu == null)
            System.out.println(numberOfOptions + ". Exit");
        else
            System.out.println(numberOfOptions + ". Back");
    }

    public void execute(){
        Menu nextMenu = null;
        int chosenMenu = getNumberOfNextMenu(numberOfOptions);
        if(chosenMenu == numberOfOptions - 1) {
            if(AccountController.getInstance().isLogin()) {
                logout();
                nextMenu = new MainMenu();
            }else
                nextMenu = new MainMenu().getRegisterOrLogin(this);
        } else if(chosenMenu == numberOfOptions){
            if(parentMenu == null) {
                try {
                    Loader.getLoader().saveData();
                } catch (DataException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Have a nice day!");
                System.exit(1);
            } else
                nextMenu = parentMenu;
        } else if(chosenMenu > subMenus.size()) {
//            nextMenu = goToShoppingOrAccountMenus(chosenMenu);
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

//    private Menu goToShoppingOrAccountMenus(int chosenMenu) {
//        if(!(this instanceof PeopleAccountMenu) && AccountController.getInstance().isLogin()) {
//            if(chosenMenu == subMenus.size() + 1) {
//                return goToAccount();
//            }
//        }
//        if(!(this instanceof ProductsMenu)) {
//            if(!(this instanceof OffsMenu)) {
//                if(chosenMenu == numberOfOptions - 3) {
//                    return new ProductsMenu(this);
//                }
//            } else {
//                if(chosenMenu == numberOfOptions - 2) {
//                    return new ProductsMenu(this);
//                }
//            }
//        }
//        if(!(this instanceof OffsMenu)) {
//            if(chosenMenu == numberOfOptions - 2) {
//                return new OffsMenu(this);
//            }
//        }
//        return null;
//    }

//    private Menu goToAccount() {
//        User user = AccountController.getInstance().getThisUser();
//        if(user instanceof Customer) {
//            return new CustomerAccount(this);
//        } else if(user instanceof Seller) {
//            return new SellerAccount(this);
//        } else {
//            return new ManagersAccount(this);
//        }
//    }

    public int getNumberOfNextMenu(int numberOfSubmenus) {
        int chosenMenu;
        while(true) {
            if(parentMenu == null) {
                chosenMenu = Integer.parseInt(getValidInput(ValidInput.INTEGER,
                        "Please write the number of one of the options."));
            } else {
                chosenMenu = Integer.parseInt(getValidInput(ValidInput.INTEGER,
                        "Please write the number of one of the options, or enter back."));
            }
            if(chosenMenu <= numberOfSubmenus) {
                break;
            } else {
                System.out.println("Please write the number of one of the options.");
            }
        }
        return chosenMenu;
    }

    protected String getValidInput(ValidInput regex, String message) {
        String input;
        while (!regex.getStringMatcher(input = scanner.nextLine().trim()).matches()) {
            goBack(parentMenu, input);
            System.out.println(message);
        }
        goBack(parentMenu, input);
        return input;
    }

    public void logout() {
        AccountController.getInstance().logout();
        System.out.println("Have a nice day!");
        if(this instanceof PeopleAccountMenu) {
            this.parentMenu.show();
            this.parentMenu.execute();
        }
    }

    public void goBack(Menu parentMenu, String input) {
        if(parentMenu == null) {

        } else if(input.equalsIgnoreCase("back")) {
            parentMenu.show();
            parentMenu.execute();
        }
    }
}
