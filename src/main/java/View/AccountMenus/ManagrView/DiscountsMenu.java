package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;
import model.DiscountCode;
import model.users.User;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscountsMenu extends Menu {
    ManagerAccountController managerAccountController;

    public DiscountsMenu(Menu parentMenu) {
        super("Manage Discounts Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getShowAllDiscountCodes());
        submenus.put(2, getCreateDiscount());
        submenus.put(3, getViewDiscount());
        submenus.put(4, getEditDiscount());
        submenus.put(5, getRemoveDiscount());
        setSubMenus(submenus);
    }

    public Menu getShowAllDiscountCodes(){
        return new Menu("View all disount codes", this) {
            @Override
            public void show() {
                for (DiscountCode discountCode : managerAccountController.getAllDiscountCodes()) {
                    System.out.println(discountCode);
                    System.out.println("--------------------------------------------------");
                }
                System.out.println("=====================================================");
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

    public Menu getCreateDiscount(){
        return new Menu("Create discount code", this) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter starting date in this format: dd-MM-yyyy HH:mm");
                String start = scanner.nextLine();
                System.out.println("Enter ending date in this format: dd-MM-yyyy HH:mm");
                String end = scanner.nextLine();
                System.out.println("Enter discount percentage: ");
                int percentage = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER, "You can just use NUMERIC characters!"));
                System.out.println("Enter maximum discount amount for a product: ");
                double max = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE, "You can just use NUMERIC characters!"));
                System.out.println("Enter number of codes per user: ");
                int count = Integer.parseInt(getValidInput(ConsoleCommand.INTEGER, "You can just use NUMERIC characters!"));
                System.out.println("Enter included usernames. Enter OK to end.");
                ArrayList <String> allUsernames = new ArrayList<>();
                String username;
                while (!(username = scanner.nextLine()).equalsIgnoreCase("Ok")){
                    if (User.doesUserExist(username))
                        allUsernames.add(username);
                }
                try {
                    managerAccountController.createDiscount(start, end, percentage, max, count, allUsernames);
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getViewDiscount(){
        return new Menu("View discount", this) {
            @Override
            public void show() {
                System.out.println("Enter a discount code or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        System.out.println(managerAccountController.getDiscount(input));
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getRemoveDiscount(){
        return new Menu("Remove discount", this) {
            @Override
            public void show() {
                System.out.println("Enter a discount code or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        managerAccountController.removeDiscount(input);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getEditDiscount(){
        return new Menu("Edit discount", this) {
            private void handleEditDiscount(String code) {
                String oldField;
                String newField;
                while (!(oldField = scanner.nextLine()).equalsIgnoreCase("Ok")) {
                    newField = scanner.nextLine();
                    try {
                        managerAccountController.editDiscount(code, oldField, newField);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                        if (e.getMessage().equalsIgnoreCase("Discount code not found."))
                            break;
                    }
                }
            }

            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter a discount code: ");
                String code = scanner.nextLine();
                System.out.println("Enter OK to exit.");
                handleEditDiscount(code);
                this.parentMenu.show();
                this.parentMenu.execute();
                }
            };
        }
}

