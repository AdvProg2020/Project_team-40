package view.account_menus.manager_view.category_view;

import controller.accounts.ManagerAccountController;
import view.Menu;
import exceptions.AccountsException;
import model.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryMenu extends Menu {
    ManagerAccountController managerAccountController;

    public CategoryMenu(Menu parentMenu) {
        super("Manage Category Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowAllCategories());
        submenus.put(2, getAddCategories());
        submenus.put(3, getRemoveCategories());
        submenus.put(4, getEditCategories());
        setSubMenus(submenus);
    }

    public Menu getShowAllCategories(){
        return new Menu("View all categories", this) {
            @Override
            public void show() {
                for (String category : managerAccountController.getAllCategories()) {
                    System.out.println(Category.getCategoryByName(category));
                    System.out.println("--------------------------------------------------");
                }
                System.out.println("Enter anything to return");

            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getAddCategories() {
        return new Menu("Add category", this) {
            private String getParentInput(String input){
                if (input.equalsIgnoreCase(""))
                    return null;
                return input;
            }
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter category name: ");
                String categoryName = scanner.nextLine();
                System.out.println("Enter parent category or ENTER to continue: ");
                String parent = getParentInput(scanner.nextLine());
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
                finally {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
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
        return new Menu("Edit category", this) {
            private void handleEditCategory(String name) {
                String oldField;
                String newField;
                while (true) {
                    System.out.println("Field: ");
                    oldField = scanner.nextLine();
                    if (oldField.equalsIgnoreCase("ok"))
                        break;
                    System.out.println("New field: ");
                    newField = scanner.nextLine();
                    try {
                        managerAccountController.editCategory(name, oldField, newField);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                        if (e.getMessage().equalsIgnoreCase("Category not found."))
                            break;
                    }
                }
            }

            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter a category name: ");
                String code = scanner.nextLine();
                System.out.println("Enter OK to exit.");
                handleEditCategory(code);
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };

    }
}



