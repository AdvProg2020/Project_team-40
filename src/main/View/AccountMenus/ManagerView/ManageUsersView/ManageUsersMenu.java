package View.AccountMenus.ManagerView.ManageUsersView;

import Controller.Accounts.AccountController;
import Controller.Accounts.ManagerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;

import java.util.HashMap;

public class ManageUsersMenu extends Menu {
    ManagerAccountController managerAccountController;

    public ManageUsersMenu(Menu parentMenu) {
        super("Manage Users Menu", parentMenu);
        managerAccountController = ManagerAccountController.getInstance();
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, getViewUsers());
        submenus.put(2, getViewUser());
        submenus.put(3, getDeleteUser());
        submenus.put(4, getCreateManager());
        setSubMenus(submenus);
    }

    public Menu getViewUsers(){
        return new Menu("View all users", this) {
            @Override
            public void show() {
                for (String userName : managerAccountController.getAllUserNames()) {
                    System.out.println(userName);
                }
                System.out.println("Enter anything return:");
            }

            @Override
            public void execute() {
                scanner.nextLine();
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
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
                        System.out.println(managerAccountController.getUser(input));
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
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
                        managerAccountController.deleteUser(input);
                    } catch (AccountsException e) {
                        System.out.println(e.getMessage());
                    }
                }
                this.parentMenu.show();
                this.parentMenu.execute();
            }
        };
    }

    public Menu getCreateManager() {
        return new Menu("Create manager menu", this) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter username: ");
                String username = scanner.nextLine();
                if (AccountController.getInstance().doesUserExistWithThisUsername(username)){
                    System.out.println("User exists with this name.");
                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
                else {
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.println("First name:");
                    String firstName = getValidInput(ConsoleCommand.NAME,
                            "Your name can only contain alphabetic characters.");
                    System.out.println("Last name:");
                    String lastName = getValidInput(ConsoleCommand.NAME,
                            "Your name can only contain alphabetic characters.");
                    System.out.println("Email address:");
                    String email = getValidInput(ConsoleCommand.EMAIL_ADDRESS, "Invalid email address");
                    System.out.println("Phone number:");
                    String phoneNumber = getValidInput(ConsoleCommand.PHONE_NUMBER, "Invalid phone number");
                    managerAccountController.createManagerAccount(username, password, firstName, lastName, email, phoneNumber);

                    this.parentMenu.show();
                    this.parentMenu.execute();
                }
            }
        };
    }
}
