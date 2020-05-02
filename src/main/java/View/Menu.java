package View;

import Controller.Accounts.AccountController;
import View.AccountMenus.PeopleAccountMenu;
import View.ShopingMenus.Product.ProductMenu;
import View.ShopingMenus.ProductsAndOffsMenus.OffsMenu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import model.Loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Menu {
    protected String name;
    protected String input;
    protected Menu parentMenu;
    protected HashMap<Integer, Menu> subMenus;
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
        int numberOfOptions = subMenus.size() + 1;
        for(Integer menuNumber: subMenus.keySet()) {
            System.out.println(menuNumber + ". " + subMenus.get(menuNumber).getName());
        }
/*        if(!(this instanceof PeopleAccountMenu)) {
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
 */
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
        int chosenMenu = getNumberOfNextMenu();
        if(chosenMenu == subMenus.size() + 1) {
            if(AccountController.getInstance().isLogin()) {
                logout();
            } else {
                nextMenu = new MainMenu(this).getRegisterOrLogin();
            }
        } else if(chosenMenu == subMenus.size() + 2){
            if(parentMenu == null) {
                //TODO:HANDLE SAVE AND LOAD PROBLEMS
            //    Loader.getLoader().saveData();
                System.exit(1);
            } else {
                nextMenu = parentMenu;
            }
        } else
            nextMenu = subMenus.get(chosenMenu);
        nextMenu.show();
        nextMenu.execute();
    }

    public int getNumberOfNextMenu() {
        int chosenMenu;
        while(true) {
            while (!ConsoleCommand.INTEGER.getStringMatcher(input = scanner.nextLine().trim()).matches()) {
                System.out.println("Please write the number of one of the options.");
            }
            chosenMenu = Integer.parseInt(input);
            if(chosenMenu <= subMenus.size() + 2) {
                break;
            } else {
                System.out.println("Please write the number of one of the options.");
            }
        }
        return chosenMenu;
    }

    public void logout(){
        AccountController.getInstance().logout();
        if(this instanceof PeopleAccountMenu) {
            this.parentMenu.show();
            this.parentMenu.execute();
        }
    }
}
