package view.account_menus;

import controller.accounts.AccountController;
import view.ConsoleCommand;
import view.Menu;

import java.util.ArrayList;

public abstract class PeopleAccountMenu extends Menu {

    public PeopleAccountMenu(String name, Menu parentMenu) {
        super(name, parentMenu);
    }

    public Menu getShowInfo() {
        return new Menu("User's information", this) {
            @Override
            public void show() {
                System.out.println(AccountController.getInstance().getThisUser());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getEditInfo() {
        return new Menu("Edit information", this) {
            @Override
            public void show() {
                ArrayList<String> fields = AccountController.getInstance().getFields();
                int numOfMenu = 1;
                System.out.println("You can change one of these fields:");
                for(String field: fields) {
                    System.out.println(numOfMenu + ": " + field);
                    numOfMenu++;
                }
                System.out.println(numOfMenu + ": Back");
            }

            @Override
            public void execute() {
                AccountController accountController = AccountController.getInstance();
                ArrayList<String> fields = accountController.getFields();
                int numOfNextMenu = getNumberOfNextMenu(fields.size() + 1);
                if(numOfNextMenu > fields.size()) {
                    parentMenu.show();
                    parentMenu.execute();
                } else  {
                    System.out.println("Enter new information:");
                    editInfo(numOfNextMenu, fields);
                    this.show();
                    this.execute();
                }
            }

            private void editInfo(int numOfNextMenu, ArrayList<String> fields) {
                AccountController accountController = AccountController.getInstance();
                String newInformation;
                if(numOfNextMenu == 1) {
                    newInformation = scanner.nextLine();
                    accountController.editUser("username", newInformation);
                } else if(numOfNextMenu == 2) {
                    newInformation = scanner.nextLine();
                    accountController.editUser("password", newInformation);
                } else if(numOfNextMenu == 3) {
                    newInformation = getValidInput(ConsoleCommand.NAME,
                            "Your name can only contain alphabetic characters.");
                    accountController.editUser("firstName", newInformation);
                } else if(numOfNextMenu == 4) {
                    newInformation = getValidInput(ConsoleCommand.NAME,
                            "Your name can only contain alphabetic characters.");
                    accountController.editUser("lastName", newInformation);
                } else if(numOfNextMenu == 5) {
                    newInformation = getValidInput(ConsoleCommand.EMAIL_ADDRESS, "Invalid email address");
                    accountController.editUser("email", newInformation);
                } else if(numOfNextMenu == 6) {
                    newInformation = getValidInput(ConsoleCommand.PHONE_NUMBER, "Invalid phone number");
                    accountController.editUser("phoneNumber", newInformation);
                } else if(numOfNextMenu == 7) {
                    newInformation = getValidInput(ConsoleCommand.DOUBLE, "Enter a valid number.");
                    accountController.editUser("credit", newInformation);
                } else if(numOfNextMenu == 8) {
                    newInformation = scanner.nextLine();
                    accountController.editUser("companyInfo", newInformation);
                }
            }
        };
    }
}
