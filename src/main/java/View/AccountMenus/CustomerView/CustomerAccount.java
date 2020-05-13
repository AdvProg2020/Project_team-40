package View.AccountMenus.CustomerView;

import Controller.Accounts.CustomerAccountController;
import View.AccountMenus.PeopleAccountMenu;
import View.ConsoleCommand;
import View.Menu;
import model.DiscountCode;
import model.log.Log;
import model.users.Customer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CustomerAccount extends PeopleAccountMenu {
    CustomerAccountController customerAccountController;

    public CustomerAccount(Menu parentMenu) {
        super("Customer Account", parentMenu);
        HashMap<Integer, Menu> submenus = new HashMap<>();
        submenus.put(1, new CartMenu(this));
        submenus.put(2, new OrdersMenu(this));
        submenus.put(3, getShowInfo());
        submenus.put(4, getEditInfo());
        submenus.put(5, getViewBalance());
        submenus.put(6, getViewDiscountCodes());
        submenus.put(7, getIncreaseCredit());
        this.setSubMenus(submenus);
    }

    public Menu getViewBalance(){
        return new Menu("Balance", this) {
            @Override
            public void show() {
                System.out.println("Balance: " + CustomerAccountController.getInstance().getBalance());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getViewDiscountCodes(){
        return new Menu("Discount Codes", this) {
            @Override
            public void show() {
                HashMap<String, DiscountCode> discountCodes = CustomerAccountController.getInstance().getDiscountCodes();
                System.out.println("Your discount codes: ");
                for(Map.Entry<String, DiscountCode> discountCode: discountCodes.entrySet()) {
                    System.out.println(discountCode.getValue());
                }
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    public Menu getIncreaseCredit() {
        return new Menu("Increase Credit", this) {
            @Override
            public void show() {
                getViewBalance().show();
                System.out.println("Enter the amount of money you want to add to your credit:");
            }

            @Override
            public void execute() {
                double money = Double.parseDouble(getValidInput(ConsoleCommand.DOUBLE,
                        "Please enter a valid number."));
                Customer customer = (Customer) CustomerAccountController.getInstance().getThisUser();
                customer.setCredit(customer.getCredit() + money);
                System.out.println("Credit successfully increased.");
            }
        };
    }
}
