package View.ShopingMenus.ProductsAndOffsMenus;

import Interfaces.Filterable;
import View.Menu;
import exceptions.MenuException;
import model.Product;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class FiltersMenu<E extends Filterable> extends Menu {
    E controller;

    public FiltersMenu(Menu parentMenu, E controller) {
        super("Filters Menu", parentMenu);
        this.controller = controller;
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowFilters());
        submenus.put(2, getFilterByValue());
        submenus.put(3, getFilterByRange());
        submenus.put(4, getShowCurrentFilters());
        submenus.put(5, getDisableFilters());
        setSubMenus(submenus);
    }

    public Menu getShowFilters(){
        return new Menu("Show available filters", this) {
            @Override
            public void show() {
                for (String filter : controller.getAvailableFilters()) {
                    System.out.println(filter);
                }
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getFilterByValue(){
        return new Menu("Filter by value", this){
            @Override
            public void show(){}

            @Override
            public void execute() {
                System.out.println("Enter a field name: ");
                String field = scanner.nextLine();
                System.out.println("Enter a value: ");
                String value = scanner.nextLine();
                try {
                    for (Product product : controller.filter(field, value)) {
                        System.out.println(product);
                        System.out.println("-------------------------------------");
                    }
                } catch (MenuException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getFilterByRange() {
        return new Menu("Filter by range", this) {
            @Override
            public void show(){}

            @Override
            public void execute() {
                System.out.println("Enter a field name: ");
                String field = scanner.nextLine();
                System.out.println("Enter minimum: ");
                double min = Double.parseDouble(scanner.nextLine());
                System.out.println("Enter maximum: ");
                double max = Double.parseDouble(scanner.nextLine());
                try {
                    for (Product product : controller.filter(field, min, max)) {
                        System.out.println(product);
                        System.out.println("-------------------------------------");
                    }
                } catch (MenuException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getShowCurrentFilters(){
        return new Menu("Show current filters", this) {
            @Override
            public void show() {
                for (String filter : controller.getCurrentFilters()) {
                    System.out.println(filter);
                }
                System.out.println("Enter anything to return.");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getDisableFilters(){
        return new Menu("Disable filters", this) {
            @Override
            public void show() {
                System.out.println("Enter a selected filter to disable.");
            }

            @Override
            public void execute() {
                String filter = scanner.nextLine();
                try {
                    controller.disableFilter(filter);
                } catch (MenuException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }
}
