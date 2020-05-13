package View;

import Controller.Accounts.AccountController;
import View.AccountMenus.CustomerView.CustomerAccount;
import View.AccountMenus.ManagrView.ManagersAccount;
import View.AccountMenus.PeopleAccountMenu;
import View.AccountMenus.SellerView.SellerAccount;
import View.ShopingMenus.Product.ProductMenu;
import View.ShopingMenus.ProductsAndOffsMenus.OffsMenu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import exceptions.DataException;
import model.Loader;
import model.Off;
import model.users.Customer;
import model.users.Manager;
import model.users.Seller;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
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

    public String getName() {
        return name;
    }

    public void setParentMenu(Menu parentMenu) {
        this.parentMenu = parentMenu;
    }

    public void setSubMenus(HashMap<Integer, Menu> subMenus) {
        this.subMenus = subMenus;
    }

    public void show(){//TODO: TEST IT!
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
            if(AccountController.getInstance().isLogin())
                logout();
            nextMenu = new MainMenu().getRegisterOrLogin(this);
        } else if(chosenMenu == numberOfOptions){
            if(parentMenu == null) {
                //TODO:HANDLE SAVE AND LOAD PROBLEMS
                try {
                    Loader.getLoader().saveData();
                } catch (DataException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Have a nice day!");
                System.exit(1);
            } else {
                nextMenu = parentMenu;
            }
        } else if(chosenMenu > subMenus.size()) {
            nextMenu = goToShoppingOrAccountMenus(chosenMenu);
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    private Menu goToShoppingOrAccountMenus(int chosenMenu) {
        if(!(this instanceof PeopleAccountMenu) && AccountController.getInstance().isLogin()) {
            if(chosenMenu == subMenus.size() + 1) {
                return goToAccount();
            }
        }
        if(!(this instanceof ProductsMenu)) {
            if(!(this instanceof OffsMenu)) {
                if(chosenMenu == numberOfOptions - 3) {
                    return new ProductsMenu(this);
                }
            } else {
                if(chosenMenu == numberOfOptions - 2) {
                    return new ProductsMenu(this);
                }
            }
        }
        if(!(this instanceof OffsMenu)) {
            if(chosenMenu == numberOfOptions - 2) {
                return new OffsMenu(this);
            }
        }
        return null;
    }

    private Menu goToAccount() {
        User user = AccountController.getInstance().getThisUser();
        if(user instanceof Customer) {
            return new CustomerAccount(this);
        } else if(user instanceof Seller) {
            return new SellerAccount(this);
        } else {
            return new ManagersAccount(this);
        }
    }

    public int getNumberOfNextMenu(int numberOfSubmenus) {
        int chosenMenu;
        while(true) {
            chosenMenu = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER,
                    "Please write the number of one of the options."));
            if(chosenMenu <= numberOfSubmenus) {
                break;
            } else {
                System.out.println("Please write the number of one of the options.");
            }
        }
        return chosenMenu;
    }

    protected String getValidInput(ConsoleCommand regex, String message) {
        String input;
        while (!regex.getStringMatcher(input = scanner.nextLine().trim()).matches()) {
            System.out.println(message);
        }
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
}
