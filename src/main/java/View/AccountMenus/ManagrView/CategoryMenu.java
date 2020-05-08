package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.Menu;
import exceptions.AccountsException;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryMenu extends Menu {
    ManagerAccountController managerAccountController;

    public CategoryMenu(Menu parentMenu) {
        super("Manage Category Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getAddCategories());
        submenus.put(2, getRemoveCategories());
        submenus.put(3, getEditCategories());
        setSubMenus(submenus);
    }

    @Override
    public void show() {
        for (String category : managerAccountController.getAllCategories()) {
            System.out.println(category);
            System.out.println("--------------------------------------------------");
        }
        System.out.println("=====================================================");
        super.show();
    }

    public Menu getAddCategories() {
        return new Menu("Add category", this) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter category name: ");
                String categoryName = scanner.nextLine();
                System.out.println("Enter parent category or ENTER to continue: ");
                String parent = scanner.nextLine();
                System.out.println("Enter category properties. Enter OK to end: ");
                ArrayList<String> allProperties = new ArrayList<>();
                String property;
                while (!(property = scanner.nextLine()).equalsIgnoreCase("Ok")){
                    allProperties.add(property);
                }
                try {
                    managerAccountController.createCategory(categoryName, parent, allProperties);
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getRemoveCategories() {
        return new Menu("Remove category", this) {
            @Override
            public void show() {
                System.out.println("Enter a category name to remove or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        managerAccountController.removeCategory(input);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getEditCategories() {
        return null;
    }
}



