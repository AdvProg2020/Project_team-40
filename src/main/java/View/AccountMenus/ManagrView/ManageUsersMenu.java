package View.AccountMenus.ManagrView;

import Controller.Accounts.ManagerAccountController;
import View.Menu;
import exceptions.AccountsException;

public class ManageUsersMenu extends Menu {
    ManagerAccountController managerAccountAccountController;

    public ManageUsersMenu(Menu parentMenu) {
        super("Manage Users Menu", parentMenu);

    }

    public Menu getViewUser(){
        return new Menu("View user", this) {
            @Override
            public void show() {
                System.out.println("Enter a username or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        System.out.println(managerAccountAccountController.getUser(input));
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };
    }

    public Menu getDeleteUser(){
        return new Menu("Delete user", this) {
            @Override
            public void show() {
                System.out.println("Enter a username or back to return: ");
            }

            @Override
            public void execute() {
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("Back")) {
                    this.parentMenu.show();
                    this.parentMenu.execute();
                } else {
                    try {
                        System.out.println(managerAccountAccountController.getUser(input));
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };
    }

    public Menu getCreateManager() {
        return null;
    }
}
