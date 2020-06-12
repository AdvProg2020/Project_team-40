package view.account_menus.custromer_view.account_view;

import controller.accounts.CustomerAccountController;
import view.account_menus.custromer_view.cart_view.CartMenu;
import view.account_menus.custromer_view.orders_view.OrdersMenu;
import view.account_menus.PeopleAccountMenu;
import view.ValidInput;
import view.Menu;
import model.DiscountCode;
import model.users.Customer;

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
        customerAccountController = CustomerAccountController.getInstance();
    }

    private Menu getViewBalance() {
        return new Menu("Balance", this) {
            @Override
            public void show() {
                System.out.println("Balance: " + customerAccountController.getBalance());
            }

            @Override
            public void execute() {
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }

    private Menu getViewDiscountCodes(){
        return new Menu("Discount Codes", this) {
            @Override
            public void show() {
                HashMap<String, DiscountCode> discountCodes = customerAccountController.getDiscountCodes();
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

    private Menu getIncreaseCredit() {
        return new Menu("Increase Credit", this) {
            @Override
            public void show() {
                getViewBalance().show();
                System.out.println("Enter the amount of money you want to add to your credit:");
            }

            @Override
            public void execute() {
                double money = Double.parseDouble(getValidInput(ValidInput.DOUBLE,
                        "Please enter a valid number."));
                Customer customer = (Customer) customerAccountController.getThisUser();
                customer.setCredit(customer.getCredit() + money);
                System.out.println("Credit successfully increased.");
                parentMenu.show();
                parentMenu.execute();
            }
        };
    }
}
