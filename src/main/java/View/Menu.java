package View;

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

    public void show(){

    }

    public void execute(){

    }

    public void Logout(){

    }
}
