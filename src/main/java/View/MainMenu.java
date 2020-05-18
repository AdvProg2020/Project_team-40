package View;

import Controller.Accounts.AccountController;
import Controller.Accounts.CustomerAccountController;
import Controller.Accounts.ManagerAccountController;
import Controller.Accounts.SellerAccountController;
import View.AccountMenus.CustomerView.CustomerAccount;
import View.AccountMenus.ManagrView.ManagersAccount;
import View.AccountMenus.SellerView.SellerAccount;
import View.ShopingMenus.ProductsAndOffsMenus.OffsMenu;
import View.ShopingMenus.ProductsAndOffsMenus.ProductsMenu;
import exceptions.AccountsException;
import model.users.Customer;
import model.users.Manager;
import model.users.Seller;
import model.users.User;

import javax.swing.*;
import java.util.HashMap;

public class MainMenu extends Menu{
    enum Role{CUSTOMER, MANAGER, SELLER}

    public MainMenu() {
        super("Main Menu", null);
        HashMap<Integer, Menu> subMenus = new HashMap<>();
        addProperMenusToSubmenus(subMenus);
        this.setSubMenus(subMenus);
    }

    private void addProperMenusToSubmenus(HashMap<Integer, Menu> subMenus) {
        AccountController accountController = AccountController.getInstance();
        if(accountController.isLogin()) {
            if(accountController.getThisUser() instanceof Customer) {
                subMenus.put(1, new CustomerAccount(this));
            } else if(accountController.getThisUser() instanceof Seller) {
                subMenus.put(1, new SellerAccount(this));
            } else {
                subMenus.put(1, new ManagersAccount(this));
            }
        }
    }

    public Menu getRegisterOrLogin(Menu parentMenu) {
        return new Menu("Register And Login", parentMenu) {
            @Override
            public void show() {
                System.out.println("1. Register\n" +
                        "2. Login\n" +
                        "3. Back");
            }

            @Override
            public void execute() {
                int chosenNumber = getNumberOfNextMenu(3);
                if(chosenNumber == 1) {
                    getRegister(this).show();
                    getRegister(this).execute();
                } else if(chosenNumber == 2) {
                    getLogin(this).show();
                    getLogin(this).execute();
                }
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getRegister(Menu menu) {
        return new Menu("Register", menu) {
            @Override
            public void show() {
                System.out.println("Enter back to end the process.");
            }

            @Override
            public void execute() {
                System.out.println("Enter username:");
                String username = getValidInput(ConsoleCommand.DEFAULT, "");
                System.out.println("Enter password:");
                String password = getValidInput(ConsoleCommand.DEFAULT, "");
                if(AccountController.getInstance().doesUserExistWithThisUsername(username)) {
                    System.out.println("User exists with this username.");
                    goBack(parentMenu, "back");
                }
                Role role = getRole();
                getRemainingInformation(username, password, role);
            }

            private Role getRole() {
                HashMap<Integer, Role> roles = new HashMap<>();
                int numberOfSubMenus = 2;
                roles.put(1, Role.CUSTOMER);
                roles.put(2, Role.SELLER);
                roles.put(3, Role.MANAGER);
                System.out.println("Enter your role:\n" +
                        "1. Customer\n" +
                        "2. Seller");
                if (!User.doesManagerExist()) {
                    System.out.println("3. Manager");
                    numberOfSubMenus++;
                }
                int chosenRole = getNumberOfNextMenu(numberOfSubMenus);
                return roles.get(chosenRole);
            }

            private void getRemainingInformation(String username, String password, Role role) {
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
                createAccount(username, password, firstName, lastName, email, phoneNumber, role);
            }

            private void createAccount(String username, String password, String firstName, String lastName,
                                       String email, String phoneNumber, Role role) {
                if(role.equals(Role.CUSTOMER)) {
                    System.out.println("Enter credit:");
                    double credit = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                            "Enter a valid number."));
                    CustomerAccountController.getInstance().createCustomerAccount(username, password, firstName, lastName,
                            email, phoneNumber, credit);
                } else if(role.equals(Role.MANAGER)) {
                    ManagerAccountController.getInstance().createManagerAccount(username, password, firstName,
                            lastName, email, phoneNumber);
                } else if(role.equals(Role.SELLER)){
                    System.out.println("Enter credit:");
                    double credit = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                            "Enter a valid number."));
                    System.out.println("Enter your company's information.");
                    String companyInfo = getValidInput(ConsoleCommand.DEFAULT, "");
                    SellerAccountController.getInstance().createSellerAccount(username, password, firstName,
                            lastName, email, phoneNumber, credit, companyInfo);
                }
            }
        };
    }

    public Menu getLogin(Menu menu) {
        return new Menu("Login", menu) {
            @Override
            public void show() {}

            @Override
            public void execute() {
                System.out.println("Enter your username:");
                String username = getValidInput(ConsoleCommand.DEFAULT, "");
                System.out.println("Enter your password:");
                String password = getValidInput(ConsoleCommand.DEFAULT, "");
                try {
                    AccountController.getInstance().login(username, password);
                    System.out.println("Welcome!");
                } catch (AccountsException e) {
                    System.out.println(e.getMessage());
                    parentMenu.show();
                    parentMenu.execute();
                }
            }
        };
    }
}
