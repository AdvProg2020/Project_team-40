package View.AccountMenus.ManagrView;

import Controller.Accounts.AccountController;
import Controller.Accounts.ManagerAccountController;
import View.ConsoleCommand;
import View.Menu;
import exceptions.AccountsException;

public class ManageUsersMenu extends Menu {
    ManagerAccountController managerAccountController;

    public ManageUsersMenu(Menu parentMenu) {
        super("Manage Users Menu", parentMenu);
    }

    public void show(){
        System.out.println("List of users:");
        for (String userName : managerAccountController.getAllUserNames()) {
            System.out.println(userName);
        }
        super.show();
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
